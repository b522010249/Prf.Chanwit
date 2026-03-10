# ui_main.py
from PyQt6.QtWidgets import (
    QMainWindow, QWidget, QVBoxLayout, QHBoxLayout,
    QLabel, QLineEdit, QPushButton, QTableWidget, QTableWidgetItem,
    QMessageBox, QHeaderView, QSpinBox, QDoubleSpinBox,QProgressDialog, QApplication
)
from PyQt6.QtCore import Qt, QTimer # <--- เพิ่ม QTimer ตรงนี้
from models import Product
from api_service import APIService

class MainWindow(QMainWindow):
    def __init__(self, service: APIService):
        super().__init__()
        self.service = service
        self.setWindowTitle("Cloud Inventory System - อัปเดตผ่าน Google Sheets")
        self.resize(800, 500)
        
        self._build_ui()
        self.load_data()

    def _build_ui(self):
        central = QWidget()
        self.setCentralWidget(central)
        main_layout = QVBoxLayout(central)

        # ฟอร์มกรอกข้อมูล
        form_layout = QHBoxLayout()
        self.txt_id = QLineEdit(); self.txt_id.setPlaceholderText("รหัสสินค้า")
        self.txt_name = QLineEdit(); self.txt_name.setPlaceholderText("ชื่อสินค้า")
        self.txt_category = QLineEdit(); self.txt_category.setPlaceholderText("หมวดหมู่")
        
        self.spin_qty = QSpinBox() # ช่องตัวเลขสำหรับจำนวน
        self.spin_qty.setRange(0, 99999)
        
        self.spin_price = QDoubleSpinBox() # ช่องตัวเลขทศนิยมสำหรับราคา
        self.spin_price.setRange(0.0, 999999.99)

        form_layout.addWidget(QLabel("รหัส:"))
        form_layout.addWidget(self.txt_id)
        form_layout.addWidget(QLabel("ชื่อ:"))
        form_layout.addWidget(self.txt_name)
        form_layout.addWidget(QLabel("หมวดหมู่:"))
        form_layout.addWidget(self.txt_category)
        form_layout.addWidget(QLabel("จำนวน:"))
        form_layout.addWidget(self.spin_qty)
        form_layout.addWidget(QLabel("ราคา:"))
        form_layout.addWidget(self.spin_price)

        # ปุ่มควบคุม
        btn_layout = QHBoxLayout()
        self.btn_add = QPushButton("เพิ่ม (Sync)")
        self.btn_update = QPushButton("แก้ไข (Sync)")
        self.btn_delete = QPushButton("ลบ (Sync)")
        self.btn_refresh = QPushButton("โหลดข้อมูลใหม่")
        
        self.btn_add.clicked.connect(self.add_product)
        self.btn_update.clicked.connect(self.update_product)
        self.btn_delete.clicked.connect(self.delete_product)
        self.btn_refresh.clicked.connect(self.load_data)

        btn_layout.addWidget(self.btn_add)
        btn_layout.addWidget(self.btn_update)
        btn_layout.addWidget(self.btn_delete)
        btn_layout.addWidget(self.btn_refresh)
        btn_layout.addStretch()

        # ตารางแสดงผล
        self.table = QTableWidget(0, 5)
        self.table.setHorizontalHeaderLabels(["รหัสสินค้า", "ชื่อสินค้า", "หมวดหมู่", "จำนวนคงเหลือ", "ราคา"])
        self.table.horizontalHeader().setSectionResizeMode(QHeaderView.ResizeMode.Stretch)
        self.table.setSelectionBehavior(QTableWidget.SelectionBehavior.SelectRows)
        self.table.setSelectionMode(QTableWidget.SelectionMode.SingleSelection)
        self.table.itemSelectionChanged.connect(self.on_row_selected)

        main_layout.addLayout(form_layout)
        main_layout.addLayout(btn_layout)
        main_layout.addWidget(self.table)

    def load_data(self):
            """เรียก API เพื่อดึงข้อมูลสินค้าทั้งหมดมาวาดลงตาราง พร้อมหน้าต่างโหลด"""
            
            # 1. สร้างหน้าต่างโหลด
            loading = QProgressDialog("กำลังโหลดข้อมูลจาก Google Sheets...", None, 0, 0, self)
            loading.setWindowTitle("โปรดรอสักครู่")
            loading.setWindowModality(Qt.WindowModality.WindowModal) # บล็อกหน้าต่างหลักไว้ไม่ให้กดปุ่มอื่น
            loading.setCancelButton(None) # ซ่อนปุ่มยกเลิก
            loading.show()
            QApplication.processEvents() # บังคับให้หน้าจอวาดหน้าต่างโหลดขึ้นมาทันที

            try:
                products = self.service.get_all_products()
                self.table.setRowCount(0)
                for i, p in enumerate(products):
                    self.table.insertRow(i)
                    self.table.setItem(i, 0, QTableWidgetItem(p.product_id))
                    self.table.setItem(i, 1, QTableWidgetItem(p.name))
                    self.table.setItem(i, 2, QTableWidgetItem(p.category))
                    self.table.setItem(i, 3, QTableWidgetItem(str(p.quantity)))
                    self.table.setItem(i, 4, QTableWidgetItem(str(p.price)))
                    
                # เอา _info ออกเพื่อไม่ให้เด้งบอกว่าสำเร็จทุกครั้งที่โหลด จะได้ไม่รำคาญครับ
                # self._info("โหลดข้อมูลจากอินเทอร์เน็ตสำเร็จ!") 
            except Exception as e:
                self._error(f"โหลดข้อมูลล้มเหลว: {e}")
            finally:
                # 2. ปิดหน้าต่างโหลดเมื่อทำงานเสร็จ (ไม่ว่าจะสำเร็จหรือ Error ก็ตาม)
                loading.close()

    def _get_form_product(self) -> Product:
        """อ่านค่าจากหน้าจอและสร้างเป็นออบเจกต์ Product"""
        return Product(
            product_id=self.txt_id.text().strip(),
            name=self.txt_name.text().strip(),
            category=self.txt_category.text().strip(),
            quantity=self.spin_qty.value(),
            price=self.spin_price.value()
        )

    def add_product(self):
        """เพิ่มสินค้าและโหลดข้อมูลซ้ำเพื่ออัปเดตหน้าจอ"""
        try:
            self.service.create_product(self._get_form_product())
            self.load_data()
        except Exception as e:
            self._error(f"เกิดข้อผิดพลาด: {e}")

    def update_product(self):
        """แก้ไขสินค้าและอัปเดตหน้าจอ"""
        try:
            self.service.update_product(self._get_form_product())
            self.load_data()
        except Exception as e:
            self._error(f"เกิดข้อผิดพลาด: {e}")

    def delete_product(self):
        """ลบสินค้าโดยอ้างอิงจากรหัสที่กรอก"""
        pid = self.txt_id.text().strip()
        if not pid: return
        try:
            self.service.delete_product(pid)
            self.load_data()
        except Exception as e:
            self._error(f"เกิดข้อผิดพลาด: {e}")

    def on_row_selected(self):
        """เมื่อคลิกแถวในตาราง ให้นำข้อมูลมาแสดงบนช่องกรอก (ฟอร์ม)"""
        row = self.table.currentRow()
        if row < 0: return
        self.txt_id.setText(self.table.item(row, 0).text())
        self.txt_name.setText(self.table.item(row, 1).text())
        self.txt_category.setText(self.table.item(row, 2).text())
        self.spin_qty.setValue(int(self.table.item(row, 3).text()))
        self.spin_price.setValue(float(self.table.item(row, 4).text()))

    def _info(self, msg):
        QMessageBox.information(self, "แจ้งเตือน", msg)

    def _error(self, msg):
        QMessageBox.critical(self, "ข้อผิดพลาด", msg)