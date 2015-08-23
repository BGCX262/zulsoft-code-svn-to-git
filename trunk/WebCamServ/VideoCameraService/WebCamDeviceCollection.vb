Public Class WebCamDeviceCollection
    Implements IList

    Private devList As ArrayList

    Public Sub New()
        devList = New ArrayList
    End Sub
    Public Sub CopyTo(ByVal arr As System.Array, ByVal index As Integer) Implements IList.CopyTo
        Throw New NotSupportedException("No Implementation")
    End Sub

    Default Public Property Item(ByVal index As Integer) As Object Implements IList.Item
        Get
            Return devList.Item(index)
        End Get

        Set(ByVal value As Object)
            Throw New NotSupportedException("No Implementation")
        End Set
    End Property

    Public ReadOnly Property IsFixedSize() As Boolean Implements IList.IsFixedSize
        Get
            Return True
        End Get
    End Property

    Public ReadOnly Property Count() As Integer Implements IList.Count
        Get
            Return devList.Count
        End Get
    End Property

    Public ReadOnly Property SyncRoot() As Object Implements IList.SyncRoot
        Get
            Throw New NotSupportedException("No Implementation")
        End Get
    End Property

    Public ReadOnly Property IsReadOnly() As Boolean Implements IList.IsReadOnly
        Get
            Return True
        End Get
    End Property

    Public ReadOnly Property IsSynchronized() As Boolean Implements IList.IsSynchronized
        Get
            Return False
        End Get
    End Property

    Public Function GetEnumerator() As IEnumerator Implements IList.GetEnumerator
        Return devList.GetEnumerator()
    End Function

    Public Sub Clear() Implements IList.Clear
        devList.Clear()
    End Sub

    Public Sub Insert(ByVal index As Integer, ByVal value As Object) Implements IList.Insert
        devList.Insert(index, value)
    End Sub

    Public Sub Remove(ByVal value As Object) Implements IList.Remove
        devList.Remove(value)
    End Sub

    Public Sub RemoveAt(ByVal index As Integer) Implements IList.RemoveAt
        devList.RemoveAt(index)
    End Sub

    Public Function Add(ByVal value As Object) As Integer Implements IList.Add
        devList.Add(value)
    End Function

    Public Function Contains(ByVal value As Object) As Boolean Implements IList.Contains
        Return devList.Contains(value)
    End Function

    Public Function IndexOf(ByVal value As Object) As Integer Implements IList.IndexOf
        Return devList.IndexOf(value)
    End Function

End Class
