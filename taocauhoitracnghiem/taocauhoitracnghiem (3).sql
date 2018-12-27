-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 17, 2018 lúc 03:58 PM
-- Phiên bản máy phục vụ: 10.1.36-MariaDB
-- Phiên bản PHP: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `taocauhoitracnghiem`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `dap_an`
--

CREATE TABLE `dap_an` (
  `ma_ch` int(11) NOT NULL,
  `noidung` text COLLATE utf8_unicode_ci NOT NULL,
  `lc1` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `lc2` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `lc3` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `lc4` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `Dap_An` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `made` int(11) NOT NULL,
  `trangthai` int(11) NOT NULL,
  `DoKho` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `idmon` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `dap_an`
--

INSERT INTO `dap_an` (`ma_ch`, `noidung`, `lc1`, `lc2`, `lc3`, `lc4`, `Dap_An`, `made`, `trangthai`, `DoKho`, `idmon`) VALUES
(10, '<p><strong>Điều tồi tệ nhất c&oacute; thể xảy ra khi bạn bị tấn c&ocirc;ng SQL injection l&agrave; g&igrave;</strong></p>', 'Trích xuất thông tin nhạy cảm', 'Liệt kê các chi tiết xác thực của người dùng đã đăng ký trên một trang web', 'Xóa dữ liệu hoặc thả bảng', 'Tất cả các ý trên', 'Tất cả các ý trên', 1, 1, 'Khó', 1),
(32, '<p>Tr&igrave;nh dịch PHP n&agrave;o l&agrave; tr&igrave;nh dịch bạn cho l&agrave; đ&uacute;ng?</p>', 'PHP Translator', 'PHP Interpreter', 'PHP Communicator', 'Không có câu nào đúng', 'PHP Interpreter', 1, 1, 'Dễ', 2),
(33, '<p>Chương tr&igrave;nh n&agrave;o kh&ocirc;ng thuộc v&agrave;o Hệ thống LAMP?</p>', 'MySQL', 'Apache', 'Microsoft', 'Linux', 'Microsoft', 1, 1, 'Dễ', 2),
(34, '<p>Thực thi đọan code n&agrave;o dưới đ&acirc;y để c&oacute; thể biết PHP Environment đ&atilde; được c&agrave;i đặt đ&uacute;ng?</p>', 'phpinfo()', 'phptatus()', 'phptest()', 'phpcheck()', 'phpinfo()', 1, 1, 'Khó', 2),
(35, '<p>Ai l&agrave; người đầu ti&ecirc;n ph&aacute;t minh ra PHP?</p>', 'James Gosling', 'Tim Berners-Lee', 'Todd Fast', 'Rasmus Lerdorf', 'Rasmus Lerdorf', 1, 1, 'Khó', 2),
(36, '<p>Từ HTML l&agrave; từ viết tắt của từ n&agrave;o?</p>', 'Hyperlinks and Text Markup Language', 'Home Tool Markup Language', 'Hyper Text Markup Language', 'Tất cả đều sai', 'Hyper Text Markup Language', 1, 1, 'Dễ', 2),
(38, '<p>L&agrave;m sao để khi click chuột v&agrave;o link th&igrave; tạo ra cửa sổ mới?</p>', 'a href=\"url\" new', 'a href=\"url\" target=\"new\"', 'a href=\"url\" target=\"_blank\"', 'a href=\"url\" target=\"_tag\"', 'a href=\"url\" target=\"_blank\"', 1, 1, 'Khó', 2),
(39, '<p>JavaScript l&agrave; ng&ocirc;n ngữ xử l&yacute; ở?</p>', 'Client', 'Server', 'Server/client', 'Không có dạng nào.', 'Server/client', 1, 1, 'Khó', 2),
(40, '<p>Phương thức viết chương tr&igrave;nh của Javascript như thế n&agrave;o?</p>', 'Viết riêng một trang', 'Viết chung với HTML', 'Cả hai dạng ', 'Không có dạng nào.', 'Cả hai dạng ', 1, 1, 'Dễ', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khoa`
--

CREATE TABLE `khoa` (
  `Id_khoa` int(11) NOT NULL,
  `ten_Khoa` varchar(30) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `khoa`
--

INSERT INTO `khoa` (`Id_khoa`, `ten_Khoa`) VALUES
(11, 'CNTT');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nguoidung`
--

CREATE TABLE `nguoidung` (
  `ma_NgDung` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `ten_NgDung` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `queQuan` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `gioi_Tinh` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `id_BMon` int(11) NOT NULL,
  `id_NgDung` int(11) NOT NULL,
  `matKhau` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `nguoidung`
--

INSERT INTO `nguoidung` (`ma_NgDung`, `ten_NgDung`, `queQuan`, `gioi_Tinh`, `id_BMon`, `id_NgDung`, `matKhau`) VALUES
('nam@gmail.com', 'sadas', 'cà mau', 'nam', 17, 11, 'abc@123');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thongtindethi`
--

CREATE TABLE `thongtindethi` (
  `id_DeThi` int(11) NOT NULL,
  `tieuDe` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `GhiChu` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `id_NguoiDung` int(11) NOT NULL,
  `id_monHoc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `thongtindethi`
--

INSERT INTO `thongtindethi` (`id_DeThi`, `tieuDe`, `GhiChu`, `id_NguoiDung`, `id_monHoc`) VALUES
(1, 'Thi 45 phút', 'Giữa Kỳ', 11, 1),
(7, 'Thi 45 phút', 'Giữa Kỳ', 11, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thongtin_cauhoi`
--

CREATE TABLE `thongtin_cauhoi` (
  `id_CauHoi` int(11) NOT NULL,
  `NoiDung` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `DoKho` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `thongtin_cauhoi`
--

INSERT INTO `thongtin_cauhoi` (`id_CauHoi`, `NoiDung`, `DoKho`) VALUES
(1, 'CSS là viết tắt của?', 'Dễ'),
(2, 'Muốn liên kết file HTML với  file định nghĩa CSS ta dùng dòng nào sau đây?', 'Khó');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thongtin_monhoc`
--

CREATE TABLE `thongtin_monhoc` (
  `ten_MonHoc` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `id_mon` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `thongtin_monhoc`
--

INSERT INTO `thongtin_monhoc` (`ten_MonHoc`, `id_mon`) VALUES
('OOP', 1),
('Php', 2),
('Mạng máy tính', 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `t_de`
--

CREATE TABLE `t_de` (
  `id_ChiTietDE` int(11) NOT NULL,
  `id_CauHoi` int(11) NOT NULL,
  `id_DeThi` int(11) NOT NULL,
  `Dap_An_Dung` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `t_de`
--

INSERT INTO `t_de` (`id_ChiTietDE`, `id_CauHoi`, `id_DeThi`, `Dap_An_Dung`) VALUES
(1, 1, 1, 'De 1'),
(2, 2, 1, 'De 1'),
(3, 1, 3, 'De 2'),
(4, 2, 3, 'De 2');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `dap_an`
--
ALTER TABLE `dap_an`
  ADD PRIMARY KEY (`ma_ch`),
  ADD KEY `made` (`made`),
  ADD KEY `idmon` (`idmon`);

--
-- Chỉ mục cho bảng `khoa`
--
ALTER TABLE `khoa`
  ADD PRIMARY KEY (`Id_khoa`);

--
-- Chỉ mục cho bảng `nguoidung`
--
ALTER TABLE `nguoidung`
  ADD PRIMARY KEY (`id_NgDung`),
  ADD KEY `id_BMon` (`id_BMon`);

--
-- Chỉ mục cho bảng `thongtindethi`
--
ALTER TABLE `thongtindethi`
  ADD PRIMARY KEY (`id_DeThi`),
  ADD KEY `id_NguoiDung` (`id_NguoiDung`) USING BTREE,
  ADD KEY `id_monHoc` (`id_monHoc`) USING BTREE;

--
-- Chỉ mục cho bảng `thongtin_cauhoi`
--
ALTER TABLE `thongtin_cauhoi`
  ADD PRIMARY KEY (`id_CauHoi`);

--
-- Chỉ mục cho bảng `thongtin_monhoc`
--
ALTER TABLE `thongtin_monhoc`
  ADD PRIMARY KEY (`id_mon`);

--
-- Chỉ mục cho bảng `t_de`
--
ALTER TABLE `t_de`
  ADD PRIMARY KEY (`id_ChiTietDE`),
  ADD KEY `id_DeThi` (`id_DeThi`) USING BTREE,
  ADD KEY `id_CauHoi` (`id_CauHoi`) USING BTREE;

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `dap_an`
--
ALTER TABLE `dap_an`
  MODIFY `ma_ch` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT cho bảng `khoa`
--
ALTER TABLE `khoa`
  MODIFY `Id_khoa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `nguoidung`
--
ALTER TABLE `nguoidung`
  MODIFY `id_NgDung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `thongtindethi`
--
ALTER TABLE `thongtindethi`
  MODIFY `id_DeThi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `thongtin_cauhoi`
--
ALTER TABLE `thongtin_cauhoi`
  MODIFY `id_CauHoi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `thongtin_monhoc`
--
ALTER TABLE `thongtin_monhoc`
  MODIFY `id_mon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `t_de`
--
ALTER TABLE `t_de`
  MODIFY `id_ChiTietDE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `thongtindethi`
--
ALTER TABLE `thongtindethi`
  ADD CONSTRAINT `thongtindethi_ibfk_1` FOREIGN KEY (`id_monHoc`) REFERENCES `thongtin_monhoc` (`id_mon`),
  ADD CONSTRAINT `thongtindethi_ibfk_2` FOREIGN KEY (`id_NguoiDung`) REFERENCES `nguoidung` (`id_NgDung`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
