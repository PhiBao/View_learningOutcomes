CREATE TABLE `ketquahoctap`.`hocphandahoc` 
(
 `Mssv` DECIMAL(9,0) NOT NULL PRIMARY KEY,
 `ToanCaoCap` DECIMAL(4,2) NULL ,
 `HoaHocDaiCuong` DECIMAL(4,2) NULL ,
 `PhapLuatDaiCuong` DECIMAL(4,2) NULL ,
 `VatLy1` DECIMAL(4,2) NULL , `VatLy2` DECIMAL(4,2) NULL ,
 `XacSuatThongKe` DECIMAL(4,2) NULL ,
 `PhuongPhapTinh` DECIMAL(4,2) NULL ,
 `DaiSo` DECIMAL(4,2) NULL ,
 `MacLenin` DECIMAL(4,2) NULL ,
 `DLCMVSVN` DECIMAL(4,2) NULL 
) ENGINE = InnoDB;

CREATE TABLE `ketquahoctap`.`tksinhvien` 
(
 `TaiKhoan` DECIMAL(9, 0) NOT NULL PRIMARY KEY,
 `MatKhau` TEXT CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL, 
 CONSTRAINT FK_TaiKhoan FOREIGN KEY (TaiKhoan) REFERENCES hocphandahoc(Mssv)
) ENGINE = InnoDB;