namespace DXApplication2
{
    partial class NhanVien
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(NhanVien));
            this.DataGridView = new System.Windows.Forms.DataGridView();
            this.MaNhanVien = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.TenNhanVien = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.GioiTinh = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.DiaChi = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.DienThoai = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.NgaySinh = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.bttThem = new System.Windows.Forms.Button();
            this.bttXoa = new System.Windows.Forms.Button();
            this.bttSua = new System.Windows.Forms.Button();
            this.bttLuu = new System.Windows.Forms.Button();
            this.bttHuy = new System.Windows.Forms.Button();
            this.lbMaNhanVien = new System.Windows.Forms.Label();
            this.lbTenNhanVien = new System.Windows.Forms.Label();
            this.lbGioiTinh = new System.Windows.Forms.Label();
            this.lbDienThoai = new System.Windows.Forms.Label();
            this.lbDiaChi = new System.Windows.Forms.Label();
            this.lbNgaySinh = new System.Windows.Forms.Label();
            this.txtMaNhanVien = new System.Windows.Forms.TextBox();
            this.txtTenNhanVien = new System.Windows.Forms.TextBox();
            this.txtDiaChi = new System.Windows.Forms.TextBox();
            this.chkGioiTinh = new System.Windows.Forms.CheckBox();
            this.mskDienThoai = new System.Windows.Forms.MaskedTextBox();
            this.mskNgaySinh = new System.Windows.Forms.DateTimePicker();
            this.grbThongTinNhanVien = new System.Windows.Forms.GroupBox();
            ((System.ComponentModel.ISupportInitialize)(this.DataGridView)).BeginInit();
            this.grbThongTinNhanVien.SuspendLayout();
            this.SuspendLayout();
            // 
            // DataGridView
            // 
            this.DataGridView.BackgroundColor = System.Drawing.Color.White;
            this.DataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.DataGridView.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.MaNhanVien,
            this.TenNhanVien,
            this.GioiTinh,
            this.DiaChi,
            this.DienThoai,
            this.NgaySinh});
            this.DataGridView.Location = new System.Drawing.Point(13, 130);
            this.DataGridView.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.DataGridView.Name = "DataGridView";
            this.DataGridView.Size = new System.Drawing.Size(1016, 231);
            this.DataGridView.TabIndex = 0;
            this.DataGridView.Click += new System.EventHandler(this.DataGridView_Click);
            // 
            // MaNhanVien
            // 
            this.MaNhanVien.DataPropertyName = "MaNhanVien";
            this.MaNhanVien.HeaderText = "Mã Nhân Viên";
            this.MaNhanVien.Name = "MaNhanVien";
            this.MaNhanVien.Width = 150;
            // 
            // TenNhanVien
            // 
            this.TenNhanVien.DataPropertyName = "TenNhanVien";
            this.TenNhanVien.HeaderText = "Tên Nhân Viên";
            this.TenNhanVien.Name = "TenNhanVien";
            this.TenNhanVien.Width = 150;
            // 
            // GioiTinh
            // 
            this.GioiTinh.DataPropertyName = "GioiTinh";
            this.GioiTinh.HeaderText = "Giới Tính";
            this.GioiTinh.Name = "GioiTinh";
            this.GioiTinh.Width = 150;
            // 
            // DiaChi
            // 
            this.DiaChi.DataPropertyName = "DiaChi";
            this.DiaChi.HeaderText = "Địa Chỉ";
            this.DiaChi.Name = "DiaChi";
            this.DiaChi.Width = 200;
            // 
            // DienThoai
            // 
            this.DienThoai.DataPropertyName = "DienThoai";
            this.DienThoai.HeaderText = "Điện Thoại";
            this.DienThoai.Name = "DienThoai";
            this.DienThoai.Width = 150;
            // 
            // NgaySinh
            // 
            this.NgaySinh.DataPropertyName = "NgaySinh";
            this.NgaySinh.HeaderText = "Ngày Sinh";
            this.NgaySinh.Name = "NgaySinh";
            this.NgaySinh.Width = 150;
            // 
            // bttThem
            // 
            this.bttThem.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.bttThem.Image = ((System.Drawing.Image)(resources.GetObject("bttThem.Image")));
            this.bttThem.Location = new System.Drawing.Point(25, 371);
            this.bttThem.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.bttThem.Name = "bttThem";
            this.bttThem.Size = new System.Drawing.Size(154, 43);
            this.bttThem.TabIndex = 1;
            this.bttThem.Text = "Thêm";
            this.bttThem.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttThem.UseVisualStyleBackColor = true;
            this.bttThem.Click += new System.EventHandler(this.bttThem_Click);
            // 
            // bttXoa
            // 
            this.bttXoa.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.bttXoa.Image = ((System.Drawing.Image)(resources.GetObject("bttXoa.Image")));
            this.bttXoa.Location = new System.Drawing.Point(239, 371);
            this.bttXoa.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.bttXoa.Name = "bttXoa";
            this.bttXoa.Size = new System.Drawing.Size(142, 43);
            this.bttXoa.TabIndex = 1;
            this.bttXoa.Text = "Xóa";
            this.bttXoa.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttXoa.UseVisualStyleBackColor = true;
            this.bttXoa.Click += new System.EventHandler(this.bttXoa_Click);
            // 
            // bttSua
            // 
            this.bttSua.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.bttSua.Image = ((System.Drawing.Image)(resources.GetObject("bttSua.Image")));
            this.bttSua.Location = new System.Drawing.Point(455, 371);
            this.bttSua.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.bttSua.Name = "bttSua";
            this.bttSua.Size = new System.Drawing.Size(148, 43);
            this.bttSua.TabIndex = 1;
            this.bttSua.Text = "Sửa";
            this.bttSua.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttSua.UseVisualStyleBackColor = true;
            this.bttSua.Click += new System.EventHandler(this.bttSua_Click);
            // 
            // bttLuu
            // 
            this.bttLuu.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.bttLuu.Image = ((System.Drawing.Image)(resources.GetObject("bttLuu.Image")));
            this.bttLuu.Location = new System.Drawing.Point(662, 371);
            this.bttLuu.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.bttLuu.Name = "bttLuu";
            this.bttLuu.Size = new System.Drawing.Size(159, 43);
            this.bttLuu.TabIndex = 1;
            this.bttLuu.Text = "Lưu";
            this.bttLuu.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttLuu.UseVisualStyleBackColor = true;
            this.bttLuu.Click += new System.EventHandler(this.bttLuu_Click);
            // 
            // bttHuy
            // 
            this.bttHuy.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.bttHuy.Image = ((System.Drawing.Image)(resources.GetObject("bttHuy.Image")));
            this.bttHuy.Location = new System.Drawing.Point(880, 375);
            this.bttHuy.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.bttHuy.Name = "bttHuy";
            this.bttHuy.Size = new System.Drawing.Size(161, 35);
            this.bttHuy.TabIndex = 1;
            this.bttHuy.Text = "Huy";
            this.bttHuy.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttHuy.UseVisualStyleBackColor = true;
            this.bttHuy.Click += new System.EventHandler(this.bttHuy_Click);
            // 
            // lbMaNhanVien
            // 
            this.lbMaNhanVien.AutoSize = true;
            this.lbMaNhanVien.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbMaNhanVien.Location = new System.Drawing.Point(24, 18);
            this.lbMaNhanVien.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lbMaNhanVien.Name = "lbMaNhanVien";
            this.lbMaNhanVien.Size = new System.Drawing.Size(100, 18);
            this.lbMaNhanVien.TabIndex = 2;
            this.lbMaNhanVien.Text = "Mã Nhân Viên";
            // 
            // lbTenNhanVien
            // 
            this.lbTenNhanVien.AutoSize = true;
            this.lbTenNhanVien.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbTenNhanVien.Location = new System.Drawing.Point(24, 47);
            this.lbTenNhanVien.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lbTenNhanVien.Name = "lbTenNhanVien";
            this.lbTenNhanVien.Size = new System.Drawing.Size(104, 18);
            this.lbTenNhanVien.TabIndex = 2;
            this.lbTenNhanVien.Text = "Tên Nhân Viên";
            // 
            // lbGioiTinh
            // 
            this.lbGioiTinh.AutoSize = true;
            this.lbGioiTinh.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbGioiTinh.Location = new System.Drawing.Point(24, 72);
            this.lbGioiTinh.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lbGioiTinh.Name = "lbGioiTinh";
            this.lbGioiTinh.Size = new System.Drawing.Size(62, 18);
            this.lbGioiTinh.TabIndex = 2;
            this.lbGioiTinh.Text = "Giới tính";
            // 
            // lbDienThoai
            // 
            this.lbDienThoai.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.lbDienThoai.AutoSize = true;
            this.lbDienThoai.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbDienThoai.Location = new System.Drawing.Point(484, 44);
            this.lbDienThoai.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lbDienThoai.Name = "lbDienThoai";
            this.lbDienThoai.Size = new System.Drawing.Size(74, 18);
            this.lbDienThoai.TabIndex = 2;
            this.lbDienThoai.Text = "Điện thoại";
            // 
            // lbDiaChi
            // 
            this.lbDiaChi.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.lbDiaChi.AutoSize = true;
            this.lbDiaChi.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbDiaChi.Location = new System.Drawing.Point(484, 12);
            this.lbDiaChi.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lbDiaChi.Name = "lbDiaChi";
            this.lbDiaChi.Size = new System.Drawing.Size(56, 18);
            this.lbDiaChi.TabIndex = 2;
            this.lbDiaChi.Text = "Địa Chỉ";
            this.lbDiaChi.Click += new System.EventHandler(this.label4_Click);
            // 
            // lbNgaySinh
            // 
            this.lbNgaySinh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.lbNgaySinh.AutoSize = true;
            this.lbNgaySinh.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbNgaySinh.Location = new System.Drawing.Point(484, 72);
            this.lbNgaySinh.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lbNgaySinh.Name = "lbNgaySinh";
            this.lbNgaySinh.Size = new System.Drawing.Size(73, 18);
            this.lbNgaySinh.TabIndex = 2;
            this.lbNgaySinh.Text = "Ngày sinh";
            // 
            // txtMaNhanVien
            // 
            this.txtMaNhanVien.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtMaNhanVien.Location = new System.Drawing.Point(162, 15);
            this.txtMaNhanVien.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.txtMaNhanVien.Name = "txtMaNhanVien";
            this.txtMaNhanVien.Size = new System.Drawing.Size(281, 24);
            this.txtMaNhanVien.TabIndex = 3;
            this.txtMaNhanVien.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtMaNhanVien_KeyUp);
            // 
            // txtTenNhanVien
            // 
            this.txtTenNhanVien.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtTenNhanVien.Location = new System.Drawing.Point(162, 47);
            this.txtTenNhanVien.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.txtTenNhanVien.Name = "txtTenNhanVien";
            this.txtTenNhanVien.Size = new System.Drawing.Size(281, 24);
            this.txtTenNhanVien.TabIndex = 3;
            // 
            // txtDiaChi
            // 
            this.txtDiaChi.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.txtDiaChi.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtDiaChi.Location = new System.Drawing.Point(584, 12);
            this.txtDiaChi.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.txtDiaChi.Name = "txtDiaChi";
            this.txtDiaChi.Size = new System.Drawing.Size(381, 24);
            this.txtDiaChi.TabIndex = 3;
            // 
            // chkGioiTinh
            // 
            this.chkGioiTinh.AutoSize = true;
            this.chkGioiTinh.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.chkGioiTinh.Location = new System.Drawing.Point(162, 71);
            this.chkGioiTinh.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.chkGioiTinh.Name = "chkGioiTinh";
            this.chkGioiTinh.Size = new System.Drawing.Size(59, 22);
            this.chkGioiTinh.TabIndex = 4;
            this.chkGioiTinh.Text = "Nam";
            this.chkGioiTinh.UseVisualStyleBackColor = true;
            // 
            // mskDienThoai
            // 
            this.mskDienThoai.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.mskDienThoai.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mskDienThoai.Location = new System.Drawing.Point(584, 44);
            this.mskDienThoai.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.mskDienThoai.Mask = "(999) 000-0000";
            this.mskDienThoai.Name = "mskDienThoai";
            this.mskDienThoai.Size = new System.Drawing.Size(381, 24);
            this.mskDienThoai.TabIndex = 5;
            // 
            // mskNgaySinh
            // 
            this.mskNgaySinh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.mskNgaySinh.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mskNgaySinh.Format = System.Windows.Forms.DateTimePickerFormat.Short;
            this.mskNgaySinh.Location = new System.Drawing.Point(584, 72);
            this.mskNgaySinh.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.mskNgaySinh.Name = "mskNgaySinh";
            this.mskNgaySinh.Size = new System.Drawing.Size(380, 24);
            this.mskNgaySinh.TabIndex = 6;
            // 
            // grbThongTinNhanVien
            // 
            this.grbThongTinNhanVien.BackColor = System.Drawing.Color.Green;
            this.grbThongTinNhanVien.Controls.Add(this.mskDienThoai);
            this.grbThongTinNhanVien.Controls.Add(this.chkGioiTinh);
            this.grbThongTinNhanVien.Controls.Add(this.mskNgaySinh);
            this.grbThongTinNhanVien.Controls.Add(this.txtTenNhanVien);
            this.grbThongTinNhanVien.Controls.Add(this.txtMaNhanVien);
            this.grbThongTinNhanVien.Controls.Add(this.lbDienThoai);
            this.grbThongTinNhanVien.Controls.Add(this.lbGioiTinh);
            this.grbThongTinNhanVien.Controls.Add(this.lbDiaChi);
            this.grbThongTinNhanVien.Controls.Add(this.lbTenNhanVien);
            this.grbThongTinNhanVien.Controls.Add(this.lbNgaySinh);
            this.grbThongTinNhanVien.Controls.Add(this.lbMaNhanVien);
            this.grbThongTinNhanVien.Controls.Add(this.txtDiaChi);
            this.grbThongTinNhanVien.Location = new System.Drawing.Point(13, 12);
            this.grbThongTinNhanVien.Name = "grbThongTinNhanVien";
            this.grbThongTinNhanVien.Size = new System.Drawing.Size(1016, 110);
            this.grbThongTinNhanVien.TabIndex = 7;
            this.grbThongTinNhanVien.TabStop = false;
            this.grbThongTinNhanVien.Text = "Thông Tin Nhân Viên";
            // 
            // NhanVien
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ButtonShadow;
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.ClientSize = new System.Drawing.Size(1059, 434);
            this.Controls.Add(this.grbThongTinNhanVien);
            this.Controls.Add(this.bttHuy);
            this.Controls.Add(this.bttLuu);
            this.Controls.Add(this.bttSua);
            this.Controls.Add(this.bttXoa);
            this.Controls.Add(this.bttThem);
            this.Controls.Add(this.DataGridView);
            this.DoubleBuffered = true;
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.Name = "NhanVien";
            this.ShowInTaskbar = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "NhanVien";
            this.Load += new System.EventHandler(this.NhanVien_Load);
            ((System.ComponentModel.ISupportInitialize)(this.DataGridView)).EndInit();
            this.grbThongTinNhanVien.ResumeLayout(false);
            this.grbThongTinNhanVien.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView DataGridView;
        private System.Windows.Forms.Button bttThem;
        private System.Windows.Forms.Button bttXoa;
        private System.Windows.Forms.Button bttSua;
        private System.Windows.Forms.Button bttLuu;
        private System.Windows.Forms.Button bttHuy;
        private System.Windows.Forms.Label lbMaNhanVien;
        private System.Windows.Forms.Label lbTenNhanVien;
        private System.Windows.Forms.Label lbGioiTinh;
        private System.Windows.Forms.Label lbDienThoai;
        private System.Windows.Forms.Label lbDiaChi;
        private System.Windows.Forms.Label lbNgaySinh;
        private System.Windows.Forms.TextBox txtMaNhanVien;
        private System.Windows.Forms.TextBox txtTenNhanVien;
        private System.Windows.Forms.TextBox txtDiaChi;
        private System.Windows.Forms.CheckBox chkGioiTinh;
        private System.Windows.Forms.MaskedTextBox mskDienThoai;
        private System.Windows.Forms.DateTimePicker mskNgaySinh;
        private System.Windows.Forms.DataGridViewTextBoxColumn MaNhanVien;
        private System.Windows.Forms.DataGridViewTextBoxColumn TenNhanVien;
        private System.Windows.Forms.DataGridViewTextBoxColumn GioiTinh;
        private System.Windows.Forms.DataGridViewTextBoxColumn DiaChi;
        private System.Windows.Forms.DataGridViewTextBoxColumn DienThoai;
        private System.Windows.Forms.DataGridViewTextBoxColumn NgaySinh;
        private System.Windows.Forms.GroupBox grbThongTinNhanVien;
    }
}