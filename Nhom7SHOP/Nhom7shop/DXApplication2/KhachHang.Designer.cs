namespace DXApplication2
{
    partial class KhachHang
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(KhachHang));
            this.DataGridView = new System.Windows.Forms.DataGridView();
            this.lbMaKhach = new System.Windows.Forms.Label();
            this.lbTenKhach = new System.Windows.Forms.Label();
            this.lbDiaChi = new System.Windows.Forms.Label();
            this.lbSoDienThoai = new System.Windows.Forms.Label();
            this.txtMaKhach = new System.Windows.Forms.TextBox();
            this.txtTenKhach = new System.Windows.Forms.TextBox();
            this.txtDiaChi = new System.Windows.Forms.TextBox();
            this.bttThem = new System.Windows.Forms.Button();
            this.bttXoa = new System.Windows.Forms.Button();
            this.bttSua = new System.Windows.Forms.Button();
            this.bttLuu = new System.Windows.Forms.Button();
            this.bttHuy = new System.Windows.Forms.Button();
            this.mskDienThoai = new System.Windows.Forms.MaskedTextBox();
            this.grbThongTinKhachHang = new System.Windows.Forms.GroupBox();
            ((System.ComponentModel.ISupportInitialize)(this.DataGridView)).BeginInit();
            this.grbThongTinKhachHang.SuspendLayout();
            this.SuspendLayout();
            // 
            // DataGridView
            // 
            this.DataGridView.BackgroundColor = System.Drawing.SystemColors.ButtonHighlight;
            this.DataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.DataGridView.Location = new System.Drawing.Point(38, 251);
            this.DataGridView.Name = "DataGridView";
            this.DataGridView.Size = new System.Drawing.Size(911, 150);
            this.DataGridView.TabIndex = 0;
            this.DataGridView.Click += new System.EventHandler(this.DataGridView_Click);
            // 
            // lbMaKhach
            // 
            this.lbMaKhach.AutoSize = true;
            this.lbMaKhach.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbMaKhach.Location = new System.Drawing.Point(25, 53);
            this.lbMaKhach.Name = "lbMaKhach";
            this.lbMaKhach.Size = new System.Drawing.Size(119, 29);
            this.lbMaKhach.TabIndex = 1;
            this.lbMaKhach.Text = "Mã Khách";
            // 
            // lbTenKhach
            // 
            this.lbTenKhach.AutoSize = true;
            this.lbTenKhach.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbTenKhach.Location = new System.Drawing.Point(25, 132);
            this.lbTenKhach.Name = "lbTenKhach";
            this.lbTenKhach.Size = new System.Drawing.Size(129, 29);
            this.lbTenKhach.TabIndex = 1;
            this.lbTenKhach.Text = "Tên Khách";
            // 
            // lbDiaChi
            // 
            this.lbDiaChi.AutoSize = true;
            this.lbDiaChi.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbDiaChi.Location = new System.Drawing.Point(434, 53);
            this.lbDiaChi.Name = "lbDiaChi";
            this.lbDiaChi.Size = new System.Drawing.Size(91, 29);
            this.lbDiaChi.TabIndex = 1;
            this.lbDiaChi.Text = "Địa Chỉ";
            // 
            // lbSoDienThoai
            // 
            this.lbSoDienThoai.AutoSize = true;
            this.lbSoDienThoai.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbSoDienThoai.Location = new System.Drawing.Point(434, 135);
            this.lbSoDienThoai.Name = "lbSoDienThoai";
            this.lbSoDienThoai.Size = new System.Drawing.Size(167, 29);
            this.lbSoDienThoai.TabIndex = 1;
            this.lbSoDienThoai.Text = "Số Điện Thoại";
            // 
            // txtMaKhach
            // 
            this.txtMaKhach.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtMaKhach.Location = new System.Drawing.Point(180, 47);
            this.txtMaKhach.Name = "txtMaKhach";
            this.txtMaKhach.Size = new System.Drawing.Size(207, 35);
            this.txtMaKhach.TabIndex = 2;
            this.txtMaKhach.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtMaKhach_KeyUp);
            // 
            // txtTenKhach
            // 
            this.txtTenKhach.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtTenKhach.Location = new System.Drawing.Point(185, 132);
            this.txtTenKhach.Name = "txtTenKhach";
            this.txtTenKhach.Size = new System.Drawing.Size(207, 35);
            this.txtTenKhach.TabIndex = 2;
            // 
            // txtDiaChi
            // 
            this.txtDiaChi.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtDiaChi.Location = new System.Drawing.Point(607, 47);
            this.txtDiaChi.Name = "txtDiaChi";
            this.txtDiaChi.Size = new System.Drawing.Size(207, 35);
            this.txtDiaChi.TabIndex = 2;
            // 
            // bttThem
            // 
            this.bttThem.Image = ((System.Drawing.Image)(resources.GetObject("bttThem.Image")));
            this.bttThem.Location = new System.Drawing.Point(38, 438);
            this.bttThem.Name = "bttThem";
            this.bttThem.Size = new System.Drawing.Size(142, 45);
            this.bttThem.TabIndex = 3;
            this.bttThem.Text = "Thêm";
            this.bttThem.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttThem.UseVisualStyleBackColor = true;
            this.bttThem.Click += new System.EventHandler(this.bttThem_Click);
            // 
            // bttXoa
            // 
            this.bttXoa.Image = ((System.Drawing.Image)(resources.GetObject("bttXoa.Image")));
            this.bttXoa.Location = new System.Drawing.Point(218, 438);
            this.bttXoa.Name = "bttXoa";
            this.bttXoa.Size = new System.Drawing.Size(142, 45);
            this.bttXoa.TabIndex = 3;
            this.bttXoa.Text = "Xóa";
            this.bttXoa.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttXoa.UseVisualStyleBackColor = true;
            this.bttXoa.Click += new System.EventHandler(this.bttXoa_Click);
            // 
            // bttSua
            // 
            this.bttSua.Image = ((System.Drawing.Image)(resources.GetObject("bttSua.Image")));
            this.bttSua.Location = new System.Drawing.Point(429, 438);
            this.bttSua.Name = "bttSua";
            this.bttSua.Size = new System.Drawing.Size(134, 45);
            this.bttSua.TabIndex = 3;
            this.bttSua.Text = "Sửa";
            this.bttSua.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttSua.UseVisualStyleBackColor = true;
            this.bttSua.Click += new System.EventHandler(this.bttSua_Click);
            // 
            // bttLuu
            // 
            this.bttLuu.Image = ((System.Drawing.Image)(resources.GetObject("bttLuu.Image")));
            this.bttLuu.Location = new System.Drawing.Point(599, 438);
            this.bttLuu.Name = "bttLuu";
            this.bttLuu.Size = new System.Drawing.Size(134, 45);
            this.bttLuu.TabIndex = 3;
            this.bttLuu.Text = "Lưu";
            this.bttLuu.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttLuu.UseVisualStyleBackColor = true;
            this.bttLuu.Click += new System.EventHandler(this.bttLuu_Click);
            // 
            // bttHuy
            // 
            this.bttHuy.Image = ((System.Drawing.Image)(resources.GetObject("bttHuy.Image")));
            this.bttHuy.Location = new System.Drawing.Point(815, 438);
            this.bttHuy.Name = "bttHuy";
            this.bttHuy.Size = new System.Drawing.Size(134, 45);
            this.bttHuy.TabIndex = 3;
            this.bttHuy.Text = "Hủy";
            this.bttHuy.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttHuy.UseVisualStyleBackColor = true;
            this.bttHuy.Click += new System.EventHandler(this.bttHuy_Click);
            // 
            // mskDienThoai
            // 
            this.mskDienThoai.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mskDienThoai.Location = new System.Drawing.Point(614, 138);
            this.mskDienThoai.Mask = "(999) 000-00000";
            this.mskDienThoai.Name = "mskDienThoai";
            this.mskDienThoai.Size = new System.Drawing.Size(207, 35);
            this.mskDienThoai.TabIndex = 4;
            // 
            // grbThongTinKhachHang
            // 
            this.grbThongTinKhachHang.BackColor = System.Drawing.Color.Green;
            this.grbThongTinKhachHang.Controls.Add(this.mskDienThoai);
            this.grbThongTinKhachHang.Controls.Add(this.txtDiaChi);
            this.grbThongTinKhachHang.Controls.Add(this.lbDiaChi);
            this.grbThongTinKhachHang.Controls.Add(this.lbSoDienThoai);
            this.grbThongTinKhachHang.Controls.Add(this.txtMaKhach);
            this.grbThongTinKhachHang.Controls.Add(this.lbMaKhach);
            this.grbThongTinKhachHang.Controls.Add(this.txtTenKhach);
            this.grbThongTinKhachHang.Controls.Add(this.lbTenKhach);
            this.grbThongTinKhachHang.Location = new System.Drawing.Point(38, 12);
            this.grbThongTinKhachHang.Name = "grbThongTinKhachHang";
            this.grbThongTinKhachHang.Size = new System.Drawing.Size(911, 210);
            this.grbThongTinKhachHang.TabIndex = 5;
            this.grbThongTinKhachHang.TabStop = false;
            this.grbThongTinKhachHang.Text = "Thông Tin Khách Hàng";
            // 
            // KhachHang
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ActiveBorder;
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.ClientSize = new System.Drawing.Size(984, 511);
            this.Controls.Add(this.grbThongTinKhachHang);
            this.Controls.Add(this.bttHuy);
            this.Controls.Add(this.bttLuu);
            this.Controls.Add(this.bttSua);
            this.Controls.Add(this.bttXoa);
            this.Controls.Add(this.bttThem);
            this.Controls.Add(this.DataGridView);
            this.Name = "KhachHang";
            this.ShowInTaskbar = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "KhachHang";
            this.Load += new System.EventHandler(this.KhachHang_Load);
            ((System.ComponentModel.ISupportInitialize)(this.DataGridView)).EndInit();
            this.grbThongTinKhachHang.ResumeLayout(false);
            this.grbThongTinKhachHang.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView DataGridView;
        private System.Windows.Forms.Label lbMaKhach;
        private System.Windows.Forms.Label lbTenKhach;
        private System.Windows.Forms.Label lbDiaChi;
        private System.Windows.Forms.Label lbSoDienThoai;
        private System.Windows.Forms.TextBox txtMaKhach;
        private System.Windows.Forms.TextBox txtTenKhach;
        private System.Windows.Forms.TextBox txtDiaChi;
        private System.Windows.Forms.Button bttThem;
        private System.Windows.Forms.Button bttXoa;
        private System.Windows.Forms.Button bttSua;
        private System.Windows.Forms.Button bttLuu;
        private System.Windows.Forms.Button bttHuy;
        private System.Windows.Forms.MaskedTextBox mskDienThoai;
        private System.Windows.Forms.GroupBox grbThongTinKhachHang;
    }
}