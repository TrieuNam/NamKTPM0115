namespace DXApplication2
{
    partial class ChatLieu
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ChatLieu));
            this.lbMaChatLieu = new System.Windows.Forms.Label();
            this.lbSize = new System.Windows.Forms.Label();
            this.bttThem = new System.Windows.Forms.Button();
            this.bttXoa = new System.Windows.Forms.Button();
            this.bttSua = new System.Windows.Forms.Button();
            this.bttHuy = new System.Windows.Forms.Button();
            this.bttLuu = new System.Windows.Forms.Button();
            this.grbThongTinchatLieu = new System.Windows.Forms.GroupBox();
            this.txtSize = new System.Windows.Forms.TextBox();
            this.txtMaChatLieu = new System.Windows.Forms.TextBox();
            this.DataGridView = new System.Windows.Forms.DataGridView();
            this.grbThongTinchatLieu.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.DataGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // lbMaChatLieu
            // 
            this.lbMaChatLieu.AutoSize = true;
            this.lbMaChatLieu.Location = new System.Drawing.Point(4, 30);
            this.lbMaChatLieu.Name = "lbMaChatLieu";
            this.lbMaChatLieu.Size = new System.Drawing.Size(80, 15);
            this.lbMaChatLieu.TabIndex = 2;
            this.lbMaChatLieu.Text = "Mã Chất Liệu";
            // 
            // lbSize
            // 
            this.lbSize.AutoSize = true;
            this.lbSize.Location = new System.Drawing.Point(338, 30);
            this.lbSize.Name = "lbSize";
            this.lbSize.Size = new System.Drawing.Size(31, 15);
            this.lbSize.TabIndex = 2;
            this.lbSize.Text = "Size";
            // 
            // bttThem
            // 
            this.bttThem.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.bttThem.Image = ((System.Drawing.Image)(resources.GetObject("bttThem.Image")));
            this.bttThem.Location = new System.Drawing.Point(12, 209);
            this.bttThem.Name = "bttThem";
            this.bttThem.Size = new System.Drawing.Size(106, 23);
            this.bttThem.TabIndex = 4;
            this.bttThem.Text = "Thêm";
            this.bttThem.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttThem.UseVisualStyleBackColor = true;
            this.bttThem.Click += new System.EventHandler(this.bttThem_Click);
            // 
            // bttXoa
            // 
            this.bttXoa.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.bttXoa.Image = ((System.Drawing.Image)(resources.GetObject("bttXoa.Image")));
            this.bttXoa.Location = new System.Drawing.Point(164, 209);
            this.bttXoa.Name = "bttXoa";
            this.bttXoa.Size = new System.Drawing.Size(93, 23);
            this.bttXoa.TabIndex = 4;
            this.bttXoa.Text = "Xóa";
            this.bttXoa.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttXoa.UseVisualStyleBackColor = true;
            this.bttXoa.Click += new System.EventHandler(this.bttXoa_Click);
            // 
            // bttSua
            // 
            this.bttSua.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.bttSua.Image = ((System.Drawing.Image)(resources.GetObject("bttSua.Image")));
            this.bttSua.Location = new System.Drawing.Point(291, 209);
            this.bttSua.Name = "bttSua";
            this.bttSua.Size = new System.Drawing.Size(105, 23);
            this.bttSua.TabIndex = 4;
            this.bttSua.Text = "Sửa";
            this.bttSua.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttSua.UseVisualStyleBackColor = true;
            this.bttSua.Click += new System.EventHandler(this.bttSua_Click);
            // 
            // bttHuy
            // 
            this.bttHuy.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.bttHuy.Image = ((System.Drawing.Image)(resources.GetObject("bttHuy.Image")));
            this.bttHuy.Location = new System.Drawing.Point(563, 209);
            this.bttHuy.Name = "bttHuy";
            this.bttHuy.Size = new System.Drawing.Size(112, 23);
            this.bttHuy.TabIndex = 4;
            this.bttHuy.Text = "Hủy";
            this.bttHuy.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttHuy.UseVisualStyleBackColor = true;
            this.bttHuy.Click += new System.EventHandler(this.bttHuy_Click);
            // 
            // bttLuu
            // 
            this.bttLuu.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.bttLuu.Image = ((System.Drawing.Image)(resources.GetObject("bttLuu.Image")));
            this.bttLuu.Location = new System.Drawing.Point(425, 209);
            this.bttLuu.Name = "bttLuu";
            this.bttLuu.Size = new System.Drawing.Size(111, 23);
            this.bttLuu.TabIndex = 4;
            this.bttLuu.Text = "Lưu";
            this.bttLuu.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.bttLuu.UseVisualStyleBackColor = true;
            this.bttLuu.Click += new System.EventHandler(this.bttLuu_Click);
            // 
            // grbThongTinchatLieu
            // 
            this.grbThongTinchatLieu.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.grbThongTinchatLieu.BackColor = System.Drawing.Color.Green;
            this.grbThongTinchatLieu.Controls.Add(this.txtSize);
            this.grbThongTinchatLieu.Controls.Add(this.txtMaChatLieu);
            this.grbThongTinchatLieu.Controls.Add(this.lbSize);
            this.grbThongTinchatLieu.Controls.Add(this.lbMaChatLieu);
            this.grbThongTinchatLieu.Location = new System.Drawing.Point(9, 12);
            this.grbThongTinchatLieu.Name = "grbThongTinchatLieu";
            this.grbThongTinchatLieu.Size = new System.Drawing.Size(658, 75);
            this.grbThongTinchatLieu.TabIndex = 5;
            this.grbThongTinchatLieu.TabStop = false;
            this.grbThongTinchatLieu.Text = "Thông Tin Chất Liệu";
            // 
            // txtSize
            // 
            this.txtSize.Location = new System.Drawing.Point(391, 29);
            this.txtSize.Name = "txtSize";
            this.txtSize.Size = new System.Drawing.Size(245, 20);
            this.txtSize.TabIndex = 3;
            // 
            // txtMaChatLieu
            // 
            this.txtMaChatLieu.Location = new System.Drawing.Point(99, 27);
            this.txtMaChatLieu.Name = "txtMaChatLieu";
            this.txtMaChatLieu.Size = new System.Drawing.Size(191, 20);
            this.txtMaChatLieu.TabIndex = 3;
            this.txtMaChatLieu.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtMaChatLieu_KeyUp);
            // 
            // DataGridView
            // 
            this.DataGridView.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.DataGridView.BackgroundColor = System.Drawing.SystemColors.ButtonHighlight;
            this.DataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.DataGridView.Location = new System.Drawing.Point(9, 93);
            this.DataGridView.Name = "DataGridView";
            this.DataGridView.Size = new System.Drawing.Size(666, 100);
            this.DataGridView.TabIndex = 6;
            this.DataGridView.Click += new System.EventHandler(this.DataGridView_Click);
            // 
            // ChatLieu
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ActiveBorder;
            this.ClientSize = new System.Drawing.Size(697, 273);
            this.Controls.Add(this.DataGridView);
            this.Controls.Add(this.grbThongTinchatLieu);
            this.Controls.Add(this.bttLuu);
            this.Controls.Add(this.bttHuy);
            this.Controls.Add(this.bttSua);
            this.Controls.Add(this.bttXoa);
            this.Controls.Add(this.bttThem);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Name = "ChatLieu";
            this.ShowInTaskbar = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "ChatLieu";
            this.Load += new System.EventHandler(this.ChatLieu_Load);
            this.grbThongTinchatLieu.ResumeLayout(false);
            this.grbThongTinchatLieu.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.DataGridView)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn2;
        private System.Windows.Forms.Label lbMaChatLieu;
        private System.Windows.Forms.Label lbSize;
        private System.Windows.Forms.Button bttThem;
        private System.Windows.Forms.Button bttXoa;
        private System.Windows.Forms.Button bttSua;
        private System.Windows.Forms.Button bttHuy;
        private System.Windows.Forms.Button bttLuu;
        private System.Windows.Forms.GroupBox grbThongTinchatLieu;
        private System.Windows.Forms.TextBox txtSize;
        private System.Windows.Forms.TextBox txtMaChatLieu;
        private System.Windows.Forms.DataGridView DataGridView;

    }
}