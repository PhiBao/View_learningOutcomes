USE KetQuaHocTap

/*Tao bang*/
CREATE TABLE `ketquahoctap`.`tksinhvien` 
(
 `TaiKhoan` DECIMAL(9) NOT NULL,
 `MatKhau` TEXT CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL 
) ENGINE = InnoDB;

CREATE TABLE `ketquahoctap`.`tksinhvien` 
(
 `TaiKhoan` DECIMAL(9) NOT NULL,
 `MatKhau` TEXT CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL 
) ENGINE = InnoDB;

CREATE TABLE `ketquahoctap`.`hocphandahoc` 
(
 `Mssv` DECIMAL(9,0) NOT NULL ,
 `ToanCaoCap` FLOAT(2,2) NULL ,
 `HoaHocDaiCuong` FLOAT(2,2) NULL ,
 `PhapLuatDaiCuong` FLOAT(2,2) NULL ,
 `VatLy1` FLOAT(2,2) NULL , `VatLy2` FLOAT(2,2) NULL ,
 `XacSuatThongKe` FLOAT(2,2) NULL ,
 `PhuongPhapTinh` FLOAT(2,2) NULL ,
 `DaiSo` FLOAT(2,2) NULL ,
 `MacLenin` FLOAT(2,2) NULL ,
 `DLCMVSVN` FLOAT(2,2) NULL 
) ENGINE = InnoDB;
