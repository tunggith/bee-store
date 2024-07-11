CREATE DATABASE webAo;

USE webAo;

-- Tạo bảng sanPham
CREATE TABLE sanPham(
  id INT auto_increment PRIMARY KEY,
  ma VARCHAR(40) NULL,
  ten NVARCHAR(255) NULL,
  trangThai INT
);

-- Tạo bảng MauSac
CREATE TABLE MauSac(
 id INT auto_increment PRIMARY KEY,
  ma VARCHAR(40) NULL,
  ten NVARCHAR(255) NULL,
  trangThai INT
);

-- Tạo bảng size
CREATE TABLE size(
 id INT auto_increment PRIMARY KEY,
  ma VARCHAR(40) NULL,
  ten NVARCHAR(255) NULL,
  trangThai INT
);

-- Tạo bảng ChiTietSanPham
CREATE TABLE ChiTietSanPham (
id INT auto_increment PRIMARY KEY,
IDSANPHAM INT ,
IDMAUSAC INT,
IDSIZE INT,
CHATLIEU NVARCHAR(255),
THUONGHIEU NVARCHAR(255),
XUATXU NVARCHAR(255),
GIABAN decimal,
GIANHAP decimal,
SOLUONGTON INT,
NGAYNHAP DATE,
TRANGTHAI INT,
FOREIGN KEY(IDSANPHAM) REFERENCES SANPHAM(Id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(IDMAUSAC) REFERENCES MAUSAC(Id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (IDSIZE) REFERENCES size(Id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Tạo bảng KHUYENMAI
CREATE TABLE KHUYENMAI (
ID INT auto_increment PRIMARY KEY,
MA VARCHAR(50),
TENKHUYENMAI NVARCHAR(255),
GIATRI INT,
NGAYTAO DATE,
NGAYBATDAU DATE,
NGAYKETTHUC DATE,
MOTA NVARCHAR(255),
SOLUONG INT,
TRANGTHAI INT
);

-- Tạo bảng CHUCVU
CREATE TABLE CHUCVU(
 ID INT auto_increment PRIMARY KEY,
 MA VARCHAR(50) ,
 TENCHUCVU NVARCHAR(255),
 TRANGTHAI INT
);

-- Tạo bảng NHANVIEN
CREATE TABLE NHANVIEN(
 ID INT auto_increment PRIMARY KEY,
 MA VARCHAR(50),
 TEN NVARCHAR(255),
 NAMSINH INT,
 IDCHUCVU INT,
 EMAIL VARCHAR(255),
 SDT VARCHAR(13),
 DIACHI NVARCHAR(255),
 USERNAME VARCHAR(50),
 PASSWORDS VARCHAR(50),
 TRANGTHAI INT,
 FOREIGN KEY(IDCHUCVU) REFERENCES CHUCVU(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Tạo bảng GIAOHANG
CREATE TABLE GIAOHANG(
  ID INT auto_increment PRIMARY KEY,
  NGAYDAO DATE,
  NGAYNHAN DATE,
  DIACHI NVARCHAR(255),
  PHIGIAOHANG decimal,
  TRANGTHAI INT
);

-- Tạo bảng THANHTOAN
CREATE TABLE THANHTOAN (
ID INT auto_increment PRIMARY KEY,
TENPHUONGTHUC NVARCHAR(255),
NGAYTHANHTOAN DATE,
TRANGTHAI INT
);

-- Tạo bảng KHACHHANG
CREATE TABLE KHACHHANG (
ID INT auto_increment PRIMARY KEY,
MA VARCHAR(50),
TEN NVARCHAR(255),
SDT VARCHAR(13),
DIACHI NVARCHAR(255),
TRANGTHAI INT
);

-- Tạo bảng HOADON
CREATE TABLE HOADON(
ID INT auto_increment PRIMARY KEY,
IDKHACHHANG INT,
IDNHANVIEN INT,
IDKHUYENMAI INT,
MA VARCHAR(50),
NGAYTAO DATE,
IDTHANHTOAN INT,
TONGTIEN decimal,
IDGIAOHANG INT,
TRANGTHAI INT,
FOREIGN KEY(IDKHACHHANG) REFERENCES KHACHHANG(ID) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(IDNHANVIEN) REFERENCES NHANVIEN(ID) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(IDKHUYENMAI) REFERENCES KHUYENMAI(ID) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(IDTHANHTOAN) REFERENCES THANHTOAN(ID) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(IDGIAOHANG) REFERENCES GIAOHANG(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Tạo bảng CHITIETHOADON
CREATE TABLE CHITIETHOADON(
ID INT auto_increment PRIMARY KEY,
MA VARCHAR(50),
IDHOADON INT,
IDCHITIETSANPHAM INT,
SOLUONG INT,
GIA decimal,
TONGTIEN decimal,
FOREIGN KEY(IDHOADON) REFERENCES HOADON(ID) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(IDCHITIETSANPHAM) REFERENCES ChiTietSanPham(Id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- insert data SanPham
INSERT INTO sanPham (ma, ten, trangThai) VALUES
('SP001', 'Sản phẩm 1', 1),
('SP002', 'Sản phẩm 2', 1),
('SP003', 'Sản phẩm 3', 0),
('SP004', 'Sản phẩm 4', 1),
('SP005', 'Sản phẩm 5', 0),
('SP006', 'Sản phẩm 6', 1),
('SP007', 'Sản phẩm 7', 1),
('SP008', 'Sản phẩm 8', 0),
('SP009', 'Sản phẩm 9', 1),
('SP010', 'Sản phẩm 10', 0);
select*from sanpham;
-- insert data MauSac
INSERT INTO MauSac (ma, ten, trangThai) VALUES
('MS001', 'Màu đỏ', 1),
('MS002', 'Màu xanh lá', 1),
('MS003', 'Màu xanh dương', 0),
('MS004', 'Màu vàng', 1),
('MS005', 'Màu đen', 0),
('MS006', 'Màu trắng', 1),
('MS007', 'Màu tím', 1),
('MS008', 'Màu cam', 0),
('MS009', 'Màu hồng', 1),
('MS010', 'Màu nâu', 0);
select*from mausac;
-- insert data size
INSERT INTO size (ma, ten, trangThai) VALUES
('SZ001', 'Size S', 1),
('SZ002', 'Size M', 1),
('SZ003', 'Size L', 0),
('SZ004', 'Size XL', 1),
('SZ005', 'Size XXL', 0),
('SZ006', 'Size XXXL', 1),
('SZ007', 'Size XS', 1),
('SZ008', 'Size XXS', 0),
('SZ009', 'Size XXXS', 1),
('SZ010', 'Size Free', 0);
select*from size;
-- insert data ChiTietSanPham
INSERT INTO ChiTietSanPham (IDSANPHAM, IDMAUSAC, IDSIZE, CHATLIEU, THUONGHIEU, XUATXU, GIABAN, GIANHAP, SOLUONGTON, NGAYNHAP, TRANGTHAI) VALUES
(1, 1, 1, 'Cotton', 'Brand A', 'Vietnam', 200000, 150000, 100, '2023-01-01', 1),
(2, 2, 2, 'Polyester', 'Brand B', 'China', 250000, 180000, 50, '2023-02-01', 1),
(3, 3, 3, 'Wool', 'Brand C', 'USA', 300000, 220000, 75, '2023-03-01', 0),
(4, 4, 4, 'Silk', 'Brand D', 'Italy', 350000, 250000, 60, '2023-04-01', 1),
(5, 5, 5, 'Linen', 'Brand E', 'France', 400000, 300000, 40, '2023-05-01', 0),
(6, 6, 6, 'Denim', 'Brand F', 'Japan', 450000, 350000, 90, '2023-06-01', 1),
(7, 7, 7, 'Leather', 'Brand G', 'Germany', 500000, 400000, 30, '2023-07-01', 1),
(8, 8, 8, 'Rayon', 'Brand H', 'India', 550000, 450000, 80, '2023-08-01', 0),
(9, 9, 9, 'Nylon', 'Brand I', 'Brazil', 600000, 500000, 70, '2023-09-01', 1),
(10, 10, 10, 'Cashmere', 'Brand J', 'UK', 650000, 550000, 20, '2023-10-01', 0);
select*from chitietsanpham;
-- insert data KhuyenMai
INSERT INTO KHUYENMAI (MA, TENKHUYENMAI, GIATRI, NGAYTAO, NGAYBATDAU, NGAYKETTHUC, MOTA, SOLUONG, TRANGTHAI) VALUES
('KM001', 'Khuyến mãi mùa xuân', 10, '2024-01-01', '2024-03-01', '2024-03-31', 'Giảm giá 10% cho tất cả các sản phẩm', 100, 1),
('KM002', 'Khuyến mãi mùa hè', 15, '2024-02-01', '2024-06-01', '2024-06-30', 'Giảm giá 15% cho tất cả các sản phẩm', 200, 1),
('KM003', 'Khuyến mãi mùa thu', 20, '2024-03-01', '2024-09-01', '2024-09-30', 'Giảm giá 20% cho tất cả các sản phẩm', 150, 0),
('KM004', 'Khuyến mãi mùa đông', 25, '2024-04-01', '2024-12-01', '2024-12-31', 'Giảm giá 25% cho tất cả các sản phẩm', 100, 1),
('KM005', 'Khuyến mãi đặc biệt', 30, '2024-05-01', '2024-11-01', '2024-11-30', 'Giảm giá 30% cho tất cả các sản phẩm', 50, 0),
('KM006', 'Khuyến mãi sinh nhật', 50, '2024-06-01', '2024-07-01', '2024-07-31', 'Giảm giá 50% cho tất cả các sản phẩm', 100, 1),
('KM007', 'Khuyến mãi cuối năm', 35, '2024-07-01', '2024-12-01', '2024-12-31', 'Giảm giá 35% cho tất cả các sản phẩm', 100, 1),
('KM008', 'Khuyến mãi VIP', 40, '2024-08-01', '2024-10-01', '2024-10-31', 'Giảm giá 40% cho tất cả các sản phẩm', 50, 0),
('KM009', 'Khuyến mãi khách hàng mới', 45, '2024-09-01', '2024-01-01', '2024-01-31', 'Giảm giá 45% cho tất cả các sản phẩm', 100, 1),
('KM010', 'Khuyến mãi khách hàng thân thiết', 60, '2024-10-01', '2024-08-01', '2024-08-31', 'Giảm giá 60% cho tất cả các sản phẩm', 75, 0);
select*from khuyenmai;
-- Insert data into CHUCVU
INSERT INTO CHUCVU (MA, TENCHUCVU, TRANGTHAI) VALUES
('CV001', 'Giám đốc', 1),
('CV002', 'Phó giám đốc', 1),
('CV003', 'Trưởng phòng', 1),
('CV004', 'Phó phòng', 1),
('CV005', 'Nhân viên', 1),
('CV006', 'Thực tập sinh', 1),
('CV007', 'Quản lý', 1),
('CV008', 'Kế toán', 1),
('CV009', 'Marketing', 1),
('CV010', 'Hành chính', 1);
select*from chucvu;
-- Insert data into NHANVIEN
INSERT INTO NHANVIEN (MA, TEN, NAMSINH, IDCHUCVU, EMAIL, SDT, DIACHI, USERNAME, PASSWORDS, TRANGTHAI) VALUES
('NV001', 'Nguyen Van A', 1980, 1, 'nguyenvana@example.com', '0123456789', 'Hanoi', 'nguyenvana', 'password1', 1),
('NV002', 'Tran Thi B', 1985, 2, 'tranthib@example.com', '0987654321', 'Hanoi', 'tranthib', 'password2', 1),
('NV003', 'Le Van C', 1990, 3, 'levanc@example.com', '0912345678', 'Hanoi', 'levanc', 'password3', 1),
('NV004', 'Pham Thi D', 1992, 4, 'phamthid@example.com', '0901234567', 'Hanoi', 'phamthid', 'password4', 1),
('NV005', 'Hoang Van E', 1988, 5, 'hoangvane@example.com', '0923456789', 'Hanoi', 'hoangvane', 'password5', 1),
('NV006', 'Ngo Thi F', 1995, 6, 'ngothif@example.com', '0934567890', 'Hanoi', 'ngothif', 'password6', 1),
('NV007', 'Do Van G', 1982, 7, 'dovang@example.com', '0945678901', 'Hanoi', 'dovang', 'password7', 1),
('NV008', 'Ly Thi H', 1993, 8, 'lythih@example.com', '0956789012', 'Hanoi', 'lythih', 'password8', 1),
('NV009', 'Nguyen Van I', 1987, 9, 'nguyenvani@example.com', '0967890123', 'Hanoi', 'nguyenvani', 'password9', 1),
('NV010', 'Tran Thi K', 1991, 10, 'tranthik@example.com', '0978901234', 'Hanoi', 'tranthik', 'password10', 1);
select*from nhanVien;
-- Insert data into GIAOHANG
INSERT INTO GIAOHANG (NGAYDAO, NGAYNHAN, DIACHI, PHIGIAOHANG, TRANGTHAI) VALUES
('2024-01-01', '2024-01-02', 'Hanoi', 50000, 1),
('2024-02-01', '2024-02-03', 'Hanoi', 60000, 1),
('2024-03-01', '2024-03-04', 'Hanoi', 70000, 1),
('2024-04-01', '2024-04-05', 'Hanoi', 80000, 1),
('2024-05-01', '2024-05-06', 'Hanoi', 90000, 1),
('2024-06-01', '2024-06-07', 'Hanoi', 100000, 1),
('2024-07-01', '2024-07-08', 'Hanoi', 110000, 1),
('2024-08-01', '2024-08-09', 'Hanoi', 120000, 1),
('2024-09-01', '2024-09-10', 'Hanoi', 130000, 1),
('2024-10-01', '2024-10-11', 'Hanoi', 140000, 1);
select*from  giaohang;
-- Insert data into THANHTOAN
INSERT INTO THANHTOAN (TENPHUONGTHUC, NGAYTHANHTOAN, TRANGTHAI) VALUES
('Tiền mặt', '2024-01-01', 1),
('Chuyển khoản', '2024-02-01', 1),
('Thẻ tín dụng', '2024-03-01', 1),
('Ví điện tử', '2024-04-01', 1),
('Paypal', '2024-05-01', 1),
('Tiền mặt', '2024-06-01', 1),
('Chuyển khoản', '2024-07-01', 1),
('Thẻ tín dụng', '2024-08-01', 1),
('Ví điện tử', '2024-09-01', 1),
('Paypal', '2024-10-01', 1);
select*from thanhtoan;
-- Insert data into KHACHHANG
INSERT INTO KHACHHANG (MA, TEN, SDT, DIACHI, TRANGTHAI) VALUES
('KH001', 'Nguyen Van A', '0123456789', 'Hanoi', 1),
('KH002', 'Tran Thi B', '0987654321', 'Hanoi', 1),
('KH003', 'Le Van C', '0912345678', 'Hanoi', 1),
('KH004', 'Pham Thi D', '0901234567', 'Hanoi', 1),
('KH005', 'Hoang Van E', '0923456789', 'Hanoi', 1),
('KH006', 'Ngo Thi F', '0934567890', 'Hanoi', 1),
('KH007', 'Do Van G', '0945678901', 'Hanoi', 1),
('KH008', 'Ly Thi H', '0956789012', 'Hanoi', 1),
('KH009', 'Nguyen Van I', '0967890123', 'Hanoi', 1),
('KH010', 'Tran Thi K', '0978901234', 'Hanoi', 1);
select*from hoadon;
-- Insert data into HOADON
INSERT INTO HOADON (IDKHACHHANG, IDNHANVIEN, IDKHUYENMAI, MA, NGAYTAO, IDTHANHTOAN, TONGTIEN, IDGIAOHANG, TRANGTHAI) VALUES
(1, 1, 1, 'HD001', '2024-01-01', 1, 1000000, 1, 1),
(2, 2, 2, 'HD002', '2024-02-01', 2, 2000000, 2, 1),
(3, 3, 3, 'HD003', '2024-03-01', 3, 3000000, 3, 1),
(4, 4, 4, 'HD004', '2024-04-01', 4, 4000000, 4, 1),
(5, 5, 5, 'HD005', '2024-05-01', 5, 5000000, 5, 1),
(6, 6, 6, 'HD006', '2024-06-01', 6, 6000000, 6, 1),
(7, 7, 7, 'HD007', '2024-07-01', 7, 7000000, 7, 1),
(8, 8, 8, 'HD008', '2024-08-01', 8, 8000000, 8, 1),
(9, 9, 9, 'HD009', '2024-09-01', 9, 9000000, 9, 1),
(10, 10, 10, 'HD010', '2024-10-01', 10, 10000000, 10, 1);
select*from chitiethoadon;
-- Insert data into CHITIETHOADON
INSERT INTO CHITIETHOADON (MA, IDHOADON, IDCHITIETSANPHAM, SOLUONG, GIA, TONGTIEN) VALUES
('CTHD001', 1, 1, 2, 500000, 1000000),
('CTHD002', 2, 2, 4, 500000, 2000000),
('CTHD003', 3, 3, 6, 500000, 3000000),
('CTHD004', 4, 4, 8, 500000, 4000000),
('CTHD005', 5, 5, 10, 500000, 5000000),
('CTHD006', 6, 6, 2, 3000000, 6000000),
('CTHD007', 7, 7, 3, 4000000, 7000000),
('CTHD008', 8, 8, 4, 5000000, 8000000),
('CTHD009', 9, 9, 5, 6000000, 9000000),
('CTHD010', 10, 10, 1, 7000000, 10000000);



