# api_service.py
import requests
from typing import List
from models import Product

class APIService:
    def __init__(self, web_app_url: str):
        # เก็บ URL ของ Google Apps Script ที่ได้จากการ Deploy
        self.url = web_app_url

    def get_all_products(self) -> List[Product]:
        """ดึงข้อมูลสินค้าทั้งหมดจาก Google Sheets ผ่าน HTTP GET"""
        response = requests.get(self.url)
        response.raise_for_status() # เช็คว่าการเชื่อมต่อเน็ตมีปัญหาหรือไม่
        data = response.json()
        
        products = []
        for item in data:
            if not str(item.get("product_id")).strip(): 
                continue # ข้ามแถวที่รหัสว่างเปล่า
            products.append(Product(
                product_id=str(item.get("product_id")),
                name=str(item.get("name")),
                category=str(item.get("category")),
                quantity=int(item.get("quantity", 0)),
                price=float(item.get("price", 0.0))
            ))
        return products

    def create_product(self, product: Product) -> None:
        """ส่งข้อมูลสินค้าใหม่ไปบันทึกลงชีตผ่าน HTTP POST"""
        payload = {
            "action": "create",
            "data": {
                "product_id": product.product_id,
                "name": product.name,
                "category": product.category,
                "quantity": product.quantity,
                "price": product.price
            }
        }
        response = requests.post(self.url, json=payload)
        response.raise_for_status()

    def update_product(self, product: Product) -> None:
        """ส่งข้อมูลสินค้าที่แก้ไขแล้วไปอัปเดตทับข้อมูลเดิมในชีต"""
        payload = {
            "action": "update",
            "data": {
                "product_id": product.product_id,
                "name": product.name,
                "category": product.category,
                "quantity": product.quantity,
                "price": product.price
            }
        }
        response = requests.post(self.url, json=payload)
        response.raise_for_status()

    def delete_product(self, product_id: str) -> None:
        """ส่งรหัสสินค้าไปลบแถวนั้นๆ ออกจากชีต"""
        payload = {
            "action": "delete",
            "product_id": product_id
        }
        response = requests.post(self.url, json=payload)
        response.raise_for_status()