Imports System.Runtime.InteropServices

Public Class WebCam


    Const WM_CAP_START As Integer = &H400S
    Const WS_CHILD As Integer = &H40000000
    Const WS_VISIBLE As Integer = &H10000000

    Const WM_CAP_DRIVER_CONNECT As Integer = WM_CAP_START + 10
    Const WM_CAP_DRIVER_DISCONNECT As Integer = WM_CAP_START + 11
    Const WM_CAP_EDIT_COPY As Integer = WM_CAP_START + 30
    Const WM_CAP_SEQUENCE As Integer = WM_CAP_START + 62
    Const WM_CAP_FILE_SAVEAS As Integer = WM_CAP_START + 23

    Const WM_CAP_SET_SCALE As Integer = WM_CAP_START + 53
    Const WM_CAP_SET_PREVIEWRATE As Integer = WM_CAP_START + 52
    Const WM_CAP_SET_PREVIEW As Integer = WM_CAP_START + 50
    Const WM_CAP_SET_OVERLAY As Integer = WM_CAP_START + 51
    Const WM_CAP_GRAB_FRAME_NOSTOP As Integer = WM_CAP_START + 61

    Const SWP_NOMOVE As Integer = &H2S
    Const SWP_NOSIZE As Integer = 1
    Const SWP_NOZORDER As Integer = &H4S
    Const HWND_BOTTOM As Integer = 1

    '    typedef struct { 
    '    UINT   wDeviceIndex; 
    '    BOOL   fHasOverlay; 
    '    BOOL   fHasDlgVideoSource; 
    '    BOOL   fHasDlgVideoFormat; 
    '    BOOL   fHasDlgVideoDisplay; 
    '    BOOL   fCaptureInitialized; 
    '    BOOL   fDriverSuppliesPalettes; 
    '    HANDLE hVideoIn; 
    '    HANDLE hVideoOut; 
    '    HANDLE hVideoExtIn; 
    '    HANDLE hVideoExtOut; 
    '} CAPDRIVERCAPS; 


    '--The capGetDriverDescription function retrieves the version 
    ' description of the capture driver--
    Private Declare Function capGetDriverDescriptionA Lib "avicap32.dll" _
       (ByVal wDriverIndex As Short, _
        ByVal lpszName As String, ByVal cbName As Integer, _
        ByVal lpszVer As String, _
        ByVal cbVer As Integer) As Boolean

    '--The capCreateCaptureWindow function creates a capture window--
    Private Declare Function capCreateCaptureWindowA Lib "avicap32.dll" _
       (ByVal lpszWindowName As String, ByVal dwStyle As Integer, _
        ByVal x As Integer, ByVal y As Integer, ByVal nWidth As Integer, _
        ByVal nHeight As Short, ByVal hWnd As Integer, _
        ByVal nID As Integer) As Integer

    '--This function sends the specified message to a window or windows--
    Private Declare Function SendMessage Lib "user32" Alias "SendMessageA" _
       (ByVal hwnd As Integer, ByVal Msg As Integer, _
        ByVal wParam As Integer, _
       <MarshalAs(UnmanagedType.AsAny)> ByVal lParam As Object) As Integer

    '--Sets the position of the window relative to the screen buffer--
    Private Declare Function SetWindowPos Lib "user32" Alias "SetWindowPos" _
       (ByVal hwnd As Integer, _
        ByVal hWndInsertAfter As Integer, ByVal x As Integer, _
        ByVal y As Integer, _
        ByVal cx As Integer, ByVal cy As Integer, _
        ByVal wFlags As Integer) As Integer

    '--This function destroys the specified window--
    Private Declare Function DestroyWindow Lib "user32" _
       (ByVal hndw As Integer) As Boolean

    '---used to identify the video source---
    Dim defDevice As WebCamDevice
    Dim webCamColl As WebCamDeviceCollection
    Dim previewRateInFrmPerSec As Integer = 10 'default to 10 frames per second

    '---used as a window handle---
    Dim hWnd As Integer

    Private Sub New()
        webCamColl = New WebCamDeviceCollection
        Me.DoCheckWebCamDevice()
        If webCamColl.Count > 0 Then
            defDevice = CType(webCamColl.Item(0), WebCamDevice)
        Else
            defDevice = Nothing
        End If
        hWnd = -1
    End Sub

    Private Shared webcamObj As WebCam

    Public Shared Function GetInstance() As WebCam
        If webcamObj Is Nothing Then
            webcamObj = New WebCam
        End If
        Return webcamObj
    End Function

    Public ReadOnly Property WebCamDevices() As WebCamDeviceCollection
        Get
            
            Return webCamColl
        End Get
    End Property

    Private Sub DoCheckWebCamDevice()
        Dim driverName As String = Space(80)
        Dim driverVersion As String = Space(80)
        Dim wcd As WebCamDevice

        For i As Short = 0 To 9
            If capGetDriverDescriptionA(i, driverName, 80, driverVersion, 80) Then
                wcd = New WebCamDevice
                wcd.Location = i
                wcd.Name = driverName.Trim()
                wcd.Version = driverVersion.Trim()
                webCamColl.Add(wcd)
            End If
        Next
    End Sub

    Public Property PreviewRate() As Integer
        Get
            Return previewRateInFrmPerSec
        End Get
        Set(ByVal value As Integer)
            If value < 0 Or value > Integer.MaxValue Then
                Throw New InvalidConstraintException()
            End If
            previewRateInFrmPerSec = value
        End Set
    End Property
    Public Sub StartPreview(ByVal ctrl As Control, Optional ByVal width As Integer = 160, Optional ByVal height As Short = 120)
        Me.hWnd = capCreateCaptureWindowA("Preview Window", _
                    WS_CHILD Or WS_VISIBLE, 0, 0, width, height, _
                    ctrl.Handle.ToInt32, defDevice.Location)

        'If CType(SendMessage(Me.hWnd, WM_CAP_DRIVER_CONNECT, defDevice.Location, 0L), Boolean) Then
        SendMessage(Me.hWnd, WM_CAP_DRIVER_CONNECT, defDevice.Location, 0L)
        SetWindowPos(Me.hWnd, HWND_BOTTOM, 0, 0, ctrl.Width, ctrl.Height, SWP_NOMOVE Or SWP_NOZORDER)
        SendMessage(Me.hWnd, WM_CAP_SET_PREVIEWRATE, CType(1000 / previewRateInFrmPerSec, Integer), 0L)
        SendMessage(Me.hWnd, WM_CAP_SET_SCALE, CType(True, Integer), 0L)
        SendMessage(Me.hWnd, WM_CAP_SET_OVERLAY, CType(True, Integer), 0L)
        SendMessage(Me.hWnd, WM_CAP_SET_PREVIEW, CType(True, Integer), 0L)

        'Else
        'DestroyWindow(hWnd)
        'End If
    End Sub

    Public Sub StartCapture(ByVal ctrl As Control, Optional ByVal width As Integer = 160, Optional ByVal height As Short = 120)
        Me.hWnd = capCreateCaptureWindowA("Capture Window", _
                    WS_CHILD Or WS_VISIBLE, 0, 0, width, height, _
                    ctrl.Handle.ToInt32, defDevice.Location)

        If CType(SendMessage(Me.hWnd, WM_CAP_DRIVER_CONNECT, defDevice.Location, 0L), Boolean) Then

            SendMessage(Me.hWnd, WM_CAP_SEQUENCE, 0, 0L)
        Else
            DestroyWindow(hWnd)
        End If
    End Sub

    Public Function CaptureImage(ByVal ctrl As Control, Optional ByVal width As Integer = 160, Optional ByVal height As Short = 120) As System.Drawing.Bitmap
        Me.hWnd = capCreateCaptureWindowA("Snap Window", _
                WS_CHILD Or WS_VISIBLE, 0, 0, width, height, _
                ctrl.Handle.ToInt32, defDevice.Location)
        If CType(SendMessage(Me.hWnd, WM_CAP_DRIVER_CONNECT, defDevice.Location, 0L), Boolean) Then
            If CType(SendMessage(Me.hWnd, WM_CAP_GRAB_FRAME_NOSTOP, 0, 0L), Boolean) = True Then
                '
            End If

        End If
    End Function

    Public Sub StopDevice()
        SendMessage(Me.hWnd, WM_CAP_DRIVER_DISCONNECT, 0, 0L)
        DestroyWindow(hWnd)
    End Sub

End Class
