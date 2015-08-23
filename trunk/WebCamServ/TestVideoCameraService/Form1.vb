Imports Com.Zulsoft.WebCamService

Public Class Form1
    Dim x As WebCam
    Private Sub Button1_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles Button1.Click
        x = WebCam.GetInstance
        x.PreviewRate = 20
        x.StartPreview(Me.PictureBox1)

    End Sub
End Class
