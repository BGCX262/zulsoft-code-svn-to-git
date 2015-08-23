Public Class WebCamDevice
    Dim m_location As Integer
    Dim m_name As String
    Dim m_version As String

    Public Property Location() As Integer
        Get
            Return m_location
        End Get
        Set(ByVal value As Integer)
            If value < 0 Or value > Integer.MaxValue Then Throw New InvalidConstraintException()
            m_location = value
        End Set
    End Property

    Public Property Name() As String
        Get
            Return m_name
        End Get
        Set(ByVal value As String)
            If value = Nothing Then m_name = "N/A"
            m_name = value
        End Set
    End Property

    Public Property Version() As String
        Get
            Return m_version
        End Get
        Set(ByVal value As String)
            If value = Nothing Then m_version = "0"
            m_version = value
        End Set
    End Property


End Class
