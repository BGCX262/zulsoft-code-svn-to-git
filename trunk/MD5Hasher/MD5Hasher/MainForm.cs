/*
 * Created by SharpDevelop.
 * User: Administrator
 * Date: 1/17/2006
 * Time: 3:35 PM
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using System.Resources;
using System.Drawing;
using System.Windows.Forms;
using System.Reflection;
using System.IO;

namespace Hasher
{
	/// <summary>
	/// Description of MainForm.
	/// </summary>
	public class MainForm : System.Windows.Forms.Form
	{
        private System.ComponentModel.IContainer components;
		private System.Windows.Forms.ToolBar toolBar1;
        private System.Windows.Forms.MenuItem mnuAddFiles;
		private System.Windows.Forms.MainMenu mainMenu1;
		private System.Windows.Forms.MenuItem menuItem3;
		private System.Windows.Forms.MenuItem mnuRemoveFiles;
		private System.Windows.Forms.MenuItem menuItem7;
		private System.Windows.Forms.MenuItem cmnuCheckMD5;
		private System.Windows.Forms.MenuItem mnuAbout;
		private System.Windows.Forms.MenuItem cmnuCalculateMD5;
		private System.Windows.Forms.MenuItem mnuRemoveAll;
		private System.Windows.Forms.MenuItem mnuFile;
		private System.Windows.Forms.MenuItem cmnuRemove;
		private System.Windows.Forms.MenuItem mnuFileExit;
		private System.Windows.Forms.ContextMenu contextMenu1;
		private System.Windows.Forms.ToolBarButton tbBtnAdd;
        private System.Windows.Forms.StatusBarPanel statHashCalculated;
		private System.Windows.Forms.ToolBarButton tbBtnAbout;
		private System.Windows.Forms.OpenFileDialog openFileDialog1;
		private System.Windows.Forms.MenuItem mnuSaveList;
		private System.Windows.Forms.StatusBar statusBar1;
		private System.Windows.Forms.ToolBarButton tbBtnRemove;
		private System.Windows.Forms.StatusBarPanel statNumFiles;
        private ToolBarButton btnBeginMD5;
        private MenuItem mnuCheckMD5;
        private Panel panel1;
        private ListView lsvFiles;
        private ColumnHeader chFilename;
        private ColumnHeader chMd5Hash;
        private ColumnHeader chMD5Status;
        private SaveFileDialog saveFileDialog1;
		private System.Windows.Forms.ImageList imageList1;
        
		public MainForm()
		{
			//
			// The InitializeComponent() call is required for Windows Forms designer support.
			//
			InitializeComponent();
			
			//
			// TODO: Add constructor code after the InitializeComponent() call.
			//
            //resManager = new ResourceManager("GlobalRes", Assembly.GetExecutingAssembly());
		}
		
		
		#region Windows Forms Designer generated code
		/// <summary>
		/// This method is required for Windows Forms designer support.
		/// Do not change the method contents inside the source code editor. The Forms designer might
		/// not be able to load this method if it was changed manually.
		/// </summary>
		private void InitializeComponent() {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.statNumFiles = new System.Windows.Forms.StatusBarPanel();
            this.tbBtnRemove = new System.Windows.Forms.ToolBarButton();
            this.statusBar1 = new System.Windows.Forms.StatusBar();
            this.statHashCalculated = new System.Windows.Forms.StatusBarPanel();
            this.mnuSaveList = new System.Windows.Forms.MenuItem();
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.tbBtnAbout = new System.Windows.Forms.ToolBarButton();
            this.tbBtnAdd = new System.Windows.Forms.ToolBarButton();
            this.contextMenu1 = new System.Windows.Forms.ContextMenu();
            this.cmnuRemove = new System.Windows.Forms.MenuItem();
            this.cmnuCheckMD5 = new System.Windows.Forms.MenuItem();
            this.cmnuCalculateMD5 = new System.Windows.Forms.MenuItem();
            this.mnuFileExit = new System.Windows.Forms.MenuItem();
            this.mnuFile = new System.Windows.Forms.MenuItem();
            this.mnuCheckMD5 = new System.Windows.Forms.MenuItem();
            this.mnuRemoveAll = new System.Windows.Forms.MenuItem();
            this.mnuAbout = new System.Windows.Forms.MenuItem();
            this.btnBeginMD5 = new System.Windows.Forms.ToolBarButton();
            this.menuItem7 = new System.Windows.Forms.MenuItem();
            this.mnuRemoveFiles = new System.Windows.Forms.MenuItem();
            this.menuItem3 = new System.Windows.Forms.MenuItem();
            this.mnuAddFiles = new System.Windows.Forms.MenuItem();
            this.mainMenu1 = new System.Windows.Forms.MainMenu(this.components);
            this.toolBar1 = new System.Windows.Forms.ToolBar();
            this.panel1 = new System.Windows.Forms.Panel();
            this.lsvFiles = new System.Windows.Forms.ListView();
            this.chFilename = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.chMd5Hash = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.chMD5Status = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.saveFileDialog1 = new System.Windows.Forms.SaveFileDialog();
            ((System.ComponentModel.ISupportInitialize)(this.statNumFiles)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.statHashCalculated)).BeginInit();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "doc.ico");
            this.imageList1.Images.SetKeyName(1, "Delete.Ico");
            this.imageList1.Images.SetKeyName(2, "sdkdiff.ico");
            this.imageList1.Images.SetKeyName(3, "About.Ico");
            // 
            // statNumFiles
            // 
            this.statNumFiles.AutoSize = System.Windows.Forms.StatusBarPanelAutoSize.Spring;
            this.statNumFiles.Name = "statNumFiles";
            this.statNumFiles.ToolTipText = "Number of Files";
            this.statNumFiles.Width = 187;
            // 
            // tbBtnRemove
            // 
            this.tbBtnRemove.ImageIndex = 1;
            this.tbBtnRemove.Name = "tbBtnRemove";
            this.tbBtnRemove.ToolTipText = "Remove Selected Files";
            // 
            // statusBar1
            // 
            this.statusBar1.Location = new System.Drawing.Point(0, 269);
            this.statusBar1.Name = "statusBar1";
            this.statusBar1.Panels.AddRange(new System.Windows.Forms.StatusBarPanel[] {
            this.statNumFiles,
            this.statHashCalculated});
            this.statusBar1.ShowPanels = true;
            this.statusBar1.Size = new System.Drawing.Size(392, 24);
            this.statusBar1.TabIndex = 1;
            // 
            // statHashCalculated
            // 
            this.statHashCalculated.AutoSize = System.Windows.Forms.StatusBarPanelAutoSize.Spring;
            this.statHashCalculated.Name = "statHashCalculated";
            this.statHashCalculated.Width = 187;
            // 
            // mnuSaveList
            // 
            this.mnuSaveList.Index = 1;
            this.mnuSaveList.Text = "&Save List";
            this.mnuSaveList.Click += new System.EventHandler(this.mnuSaveList_Click);
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.Multiselect = true;
            this.openFileDialog1.ShowReadOnly = true;
            this.openFileDialog1.Title = "Select Files";
            // 
            // tbBtnAbout
            // 
            this.tbBtnAbout.ImageIndex = 3;
            this.tbBtnAbout.Name = "tbBtnAbout";
            this.tbBtnAbout.ToolTipText = "About MD5Hasher2";
            // 
            // tbBtnAdd
            // 
            this.tbBtnAdd.ImageIndex = 0;
            this.tbBtnAdd.Name = "tbBtnAdd";
            this.tbBtnAdd.ToolTipText = "Add Files";
            // 
            // contextMenu1
            // 
            this.contextMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.cmnuRemove,
            this.cmnuCheckMD5,
            this.cmnuCalculateMD5});
            this.contextMenu1.Popup += new System.EventHandler(this.contextMenu1_Popup);
            // 
            // cmnuRemove
            // 
            this.cmnuRemove.Index = 0;
            this.cmnuRemove.Text = "Remove";
            // 
            // cmnuCheckMD5
            // 
            this.cmnuCheckMD5.Index = 1;
            this.cmnuCheckMD5.Text = "Check MD5";
            // 
            // cmnuCalculateMD5
            // 
            this.cmnuCalculateMD5.Index = 2;
            this.cmnuCalculateMD5.Text = "Calculate MD5";
            // 
            // mnuFileExit
            // 
            this.mnuFileExit.Index = 2;
            this.mnuFileExit.Text = "E&xit";
            this.mnuFileExit.Click += new System.EventHandler(this.mnuFileExit_Click);
            // 
            // mnuFile
            // 
            this.mnuFile.Index = 0;
            this.mnuFile.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuCheckMD5,
            this.mnuSaveList,
            this.mnuFileExit});
            this.mnuFile.Text = "&File";
            // 
            // mnuCheckMD5
            // 
            this.mnuCheckMD5.Index = 0;
            this.mnuCheckMD5.Shortcut = System.Windows.Forms.Shortcut.CtrlF2;
            this.mnuCheckMD5.Text = "Check MD5";
            this.mnuCheckMD5.Click += new System.EventHandler(this.mnuCheckMD5_Click);
            // 
            // mnuRemoveAll
            // 
            this.mnuRemoveAll.Index = 2;
            this.mnuRemoveAll.Text = "Remove All";
            this.mnuRemoveAll.Click += new System.EventHandler(this.mnuRemoveAll_Click);
            // 
            // mnuAbout
            // 
            this.mnuAbout.Index = 0;
            this.mnuAbout.Text = "About...";
            this.mnuAbout.Click += new System.EventHandler(this.mnuAbout_Click);
            // 
            // btnBeginMD5
            // 
            this.btnBeginMD5.ImageIndex = 2;
            this.btnBeginMD5.Name = "btnBeginMD5";
            this.btnBeginMD5.ToolTipText = "Begin MD5 Calculation and Checking";
            // 
            // menuItem7
            // 
            this.menuItem7.Index = 2;
            this.menuItem7.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuAbout});
            this.menuItem7.Text = "&Help";
            // 
            // mnuRemoveFiles
            // 
            this.mnuRemoveFiles.Index = 1;
            this.mnuRemoveFiles.Text = "&Remove File(s)";
            this.mnuRemoveFiles.Click += new System.EventHandler(this.mnuRemoveFiles_Click);
            // 
            // menuItem3
            // 
            this.menuItem3.Index = 1;
            this.menuItem3.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuAddFiles,
            this.mnuRemoveFiles,
            this.mnuRemoveAll});
            this.menuItem3.Text = "&Edit";
            // 
            // mnuAddFiles
            // 
            this.mnuAddFiles.Index = 0;
            this.mnuAddFiles.Text = "&Add File(s)...";
            this.mnuAddFiles.Click += new System.EventHandler(this.MnuAddFilesClick);
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
            this.mnuFile,
            this.menuItem3,
            this.menuItem7});
            // 
            // toolBar1
            // 
            this.toolBar1.Buttons.AddRange(new System.Windows.Forms.ToolBarButton[] {
            this.tbBtnAdd,
            this.tbBtnRemove,
            this.btnBeginMD5,
            this.tbBtnAbout});
            this.toolBar1.DropDownArrows = true;
            this.toolBar1.ImageList = this.imageList1;
            this.toolBar1.Location = new System.Drawing.Point(0, 0);
            this.toolBar1.Name = "toolBar1";
            this.toolBar1.ShowToolTips = true;
            this.toolBar1.Size = new System.Drawing.Size(392, 28);
            this.toolBar1.TabIndex = 2;
            this.toolBar1.ButtonClick += new System.Windows.Forms.ToolBarButtonClickEventHandler(this.toolBar1_ButtonClick);
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.lsvFiles);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(0, 28);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(392, 241);
            this.panel1.TabIndex = 3;
            // 
            // lsvFiles
            // 
            this.lsvFiles.AllowDrop = true;
            this.lsvFiles.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.chFilename,
            this.chMd5Hash,
            this.chMD5Status});
            this.lsvFiles.ContextMenu = this.contextMenu1;
            this.lsvFiles.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lsvFiles.Location = new System.Drawing.Point(0, 0);
            this.lsvFiles.Name = "lsvFiles";
            this.lsvFiles.Size = new System.Drawing.Size(392, 241);
            this.lsvFiles.Sorting = System.Windows.Forms.SortOrder.Ascending;
            this.lsvFiles.TabIndex = 1;
            this.lsvFiles.UseCompatibleStateImageBehavior = false;
            this.lsvFiles.View = System.Windows.Forms.View.Details;
            this.lsvFiles.DragDrop += new System.Windows.Forms.DragEventHandler(this.LsvFilesMD5DragDrop);
            this.lsvFiles.DragEnter += new System.Windows.Forms.DragEventHandler(this.lsvFilesMD5_DragEnter);
            // 
            // chFilename
            // 
            this.chFilename.Text = "Filename";
            this.chFilename.Width = 154;
            // 
            // chMd5Hash
            // 
            this.chMd5Hash.Text = "MD5 Hash";
            this.chMd5Hash.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.chMd5Hash.Width = 119;
            // 
            // chMD5Status
            // 
            this.chMD5Status.Text = "Status";
            this.chMD5Status.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // MainForm
            // 
            this.AllowDrop = true;
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.ClientSize = new System.Drawing.Size(392, 293);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.toolBar1);
            this.Controls.Add(this.statusBar1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Menu = this.mainMenu1;
            this.Name = "MainForm";
            this.Text = "Hasher";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MainForm_FormClosing);
            this.Load += new System.EventHandler(this.MainForm_Load);
            this.Shown += new System.EventHandler(this.MainForm_Shown);
            ((System.ComponentModel.ISupportInitialize)(this.statNumFiles)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.statHashCalculated)).EndInit();
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

		}
		#endregion
	
		
		void MnuAddFilesClick(object sender, System.EventArgs e)
		{
            openFileDialog1.CheckFileExists = true;
            openFileDialog1.CheckPathExists = true;
            openFileDialog1.Multiselect = true;

            DialogResult rs = openFileDialog1.ShowDialog();
			if(rs == DialogResult.OK ) {
                
                foreach (string flname in openFileDialog1.FileNames)
                {
                    ListViewItem item = lsvFiles.Items.Add(flname);
                                       
                }
                updateStatusBar();
                updateCommandUI();
			}
		}
		
		void LsvFilesMD5DragDrop(object sender, System.Windows.Forms.DragEventArgs e)
		{
            if (e.Data.GetDataPresent(DataFormats.FileDrop))
            {
                String[] s = (String[])e.Data.GetData(DataFormats.FileDrop);
                foreach (String filename in s)
                {
                   FileInfo finfo = new FileInfo(filename);

                   if (finfo.Attributes != FileAttributes.Directory)
                   {
                       ListViewItem item = lsvFiles.Items.Add(filename);
                   }
                }
                updateStatusBar();
                updateCommandUI();
            }
		}
		
		private void mnuAbout_Click(object sender, EventArgs e)
        {
            AboutBox ab = new AboutBox();
            ab.ShowDialog();
        }

        private void mnuFileExit_Click(object sender, EventArgs e)
        {
            //if (MessageBox.Show(MD5Hasher.Properties.Resources.strMsgAppsOnExit, "Exit", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
            //{
                Application.Exit();
            //}
        }

        private void toolBar1_ButtonClick(object sender, ToolBarButtonClickEventArgs e)
        {
            if (e.Button.Equals(tbBtnAdd)) {
                MnuAddFilesClick(sender, e);
            }
            else if (e.Button.Equals(tbBtnRemove)) {
                mnuRemoveFiles_Click(sender, e);
            }
            else if (e.Button.Equals(btnBeginMD5))
            {
                mnuCheckMD5_Click(sender, e);
            }
            else if (e.Button.Equals(tbBtnAbout)) {
                mnuAbout_Click(sender, e);
            }
            else
            {

            }
        }

        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (MessageBox.Show(MD5Hasher.Properties.Resources.strMsgAppsOnExit, "Exit", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.No)
            {
                e.Cancel = true;

            }
            
        }

        private void lsvFilesMD5_DragEnter(object sender, DragEventArgs e)
        {
            e.Effect = DragDropEffects.All;
        }

        private void mnuRemoveFiles_Click(object sender, EventArgs e)
        {
            if (lsvFiles.SelectedItems.Count > 0)
            {
                foreach (ListViewItem lvi in lsvFiles.SelectedItems)
                {
                    lsvFiles.Items.Remove(lvi);
                }
                updateStatusBar();
                updateCommandUI();
            }
        }

        private void mnuRemoveAll_Click(object sender, EventArgs e)
        {
            foreach (ListViewItem lvi in lsvFiles.Items)
            {
                lsvFiles.Items.Remove(lvi);
            }
            updateStatusBar();
            updateCommandUI();
        }

        private void mnuSaveList_Click(object sender, EventArgs e)
        {
            if (lsvFiles.Items.Count > 0)
            {
                saveFileDialog1.AddExtension = true;
                //saveFileDialog1.CreatePrompt = true;
                saveFileDialog1.DefaultExt = "md5";
                saveFileDialog1.Filter = "MD5 File|*.md5";
                saveFileDialog1.OverwritePrompt = true;
                if (saveFileDialog1.ShowDialog()== DialogResult.OK) {
                    StreamWriter sw = File.CreateText(saveFileDialog1.FileName);

                    foreach (ListViewItem lvi in lsvFiles.Items)
                    {
                        string x = lvi.Text.Substring(lvi.Text.LastIndexOf(@"\") + 1);
                        sw.WriteLine("{0} : {1}", x , ((lvi.SubItems.Count > 1)?lvi.SubItems[1].Text:"0"));
                    }
                    sw.Close();
                }
            }
        }

        private void mnuCheckMD5_Click(object sender, EventArgs e)
        {
               
            foreach (ListViewItem lvi in lsvFiles.Items)
            {
                if(lvi.SubItems.Count == 1) lvi.SubItems.Add(handler.CalculateHashFromFile(lvi.Text));
            }
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            updateStatusBar();
            
        }

        private void updateCommandUI()
        {
            if (this.lsvFiles.Items.Count < 1)
            {
                foreach (MenuItem i in mainMenu1.MenuItems)
                {
                    if (i.IsParent)
                    {
                        foreach (MenuItem j in i.MenuItems)
                        {
                            if (j.Text == "Check MD5") j.Enabled = false ;
                            if (j.Text == "&Save List") j.Enabled = false ;
                            if (j.Text == "&Remove File(s)") j.Enabled = false;
                            if (j.Text == "Remove All") j.Enabled = false ;
                        }
                    }
                }
                foreach (MenuItem i in contextMenu1.MenuItems)
                {
                    i.Enabled = false;
                }

                toolBar1.Buttons[1].Enabled = false;
                toolBar1.Buttons[2].Enabled = false;
                
            }
            else
            {
                foreach (MenuItem i in mainMenu1.MenuItems)
                {
                    if (i.IsParent)
                    {
                        foreach (MenuItem j in i.MenuItems)
                        {
                            if (j.Text == "Check MD5") j.Enabled = true;
                            if (j.Text == "&Save List") j.Enabled = true;
                            if (j.Text == "&Remove File(s)") j.Enabled = true;
                            if (j.Text == "Remove All") j.Enabled = true;
                        }
                    }
                }
            
                foreach (MenuItem i in contextMenu1.MenuItems)
                {
                    i.Enabled = true;
                }

                toolBar1.Buttons[1].Enabled = true;
                toolBar1.Buttons[2].Enabled = true;
            }
        }

        private void updateStatusBar()
        {
            statusBar1.Panels[0].Text = lsvFiles.Items.Count + " file" + (lsvFiles.Items.Count > 1 ? "s": "" );
        }

        private void contextMenu1_Popup(object sender, EventArgs e)
        {
            updateCommandUI();
        }

        private void MainForm_Shown(object sender, EventArgs e)
        {
            updateCommandUI();
        }

        private MD5HashHandler handler = new MD5HashHandler();
	}
}
