# main.py
import sys
from PyQt6.QtWidgets import QApplication
from api_service import APIService
from ui_main import MainWindow

def main():
    # 🔴 สำคัญ: นำ URL ที่ได้จาก Google Apps Script (ตอน Deploy) มาใส่ในเครื่องหมายคำพูดด้านล่าง
    WEB_APP_URL = "https://script.google.com/macros/s/AKfycbx73GvlYVM587qFGsUzkPHqMLgG4zPhhCXJxb65YcZHyFGzBFG4c6Adiq30OnImsG3p/exec"
    
    app = QApplication(sys.argv)
    
    # เชื่อมต่อ Service และส่งให้ MainWindow
    service = APIService(WEB_APP_URL)
    window = MainWindow(service)
    window.show()
    
    sys.exit(app.exec())

if __name__ == "__main__":
    main()