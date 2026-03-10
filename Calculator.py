while True:
    print("\n---------------------------")
    user_input = input("ใส่โจทย์ (ต้องเว้นวรรค เช่น 5 + 2) หรือพิมพ์ 'x' เพื่อหยุด: ")
    
    if user_input.lower() == 'x':
        print("จบการทำงานของโปรแกรม")
        break
        
    try:
        # แยกข้อความออกเป็น 3 ส่วน (ตัวเลข1, เครื่องหมาย, ตัวเลข2)
        parts = user_input.split()
        
        # เช็คว่าผู้ใช้ใส่มาครบ 3 ส่วนไหม (ถ้าลืมเว้นวรรคจะถือว่าไม่ครบ)
        if len(parts) != 3:
            print("ข้อผิดพลาด: กรุณาพิมพ์เว้นวรรคให้ถูกต้อง เช่น 5 + 2")
            continue
            
        num1 = float(parts[0])
        op = parts[1]
        num2 = float(parts[2])
        
        # คำนวณตามเครื่องหมาย
        if op == '+': result = num1 + num2
        elif op == '-': result = num1 - num2
        elif op == '*': result = num1 * num2
        elif op == '/': result = num1 / num2
        else:
            print("ข้อผิดพลาด: เครื่องหมายไม่ถูกต้อง")
            continue
            
        # ลบจุดทศนิยม .0 
        if result == int(result): result = int(result)
        if num1 == int(num1): num1 = int(num1)
        if num2 == int(num2): num2 = int(num2)
            
        print(f"{num1} {op} {num2} =\n{result}")
        
    except ValueError:
        print("ข้อผิดพลาด: กรุณาใส่เฉพาะตัวเลขเท่านั้น!")
    except ZeroDivisionError:
        print("ข้อผิดพลาด: ไม่สามารถหารด้วย 0 ได้!")