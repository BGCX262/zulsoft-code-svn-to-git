Public Class Form1

    Dim cardDiamond(12) As String
    Dim cardClub(12) As String
    Dim cardHeart(12) As String
    Dim cardSpade(12) As String
    Dim cardDeal(51) As String
    Dim cardNoDeal As Integer

    Dim value(12) As Integer
    Dim houseValue As Integer
    Dim playerValue As Integer

    Private Function GetValue(ByRef card As String, ByVal keep As Boolean) As Integer
        Dim cardv As Integer
        Dim val As Integer

        Dim cardAlreadyLost As Boolean = False
        Do
            'if all cards have been deal with return -2 to
            'indicate that there is no more cards on the dealer's deck
            If cardNoDeal >= 52 Then
                Return -2
            End If

            Randomize() 'menjana nombor rawak yang berbeza (jenis card)
            cardv = CInt(Rnd() * 3)

            Randomize() 'menjana nombor rawak yang berbeza (nilai card)
            val = CInt(Rnd() * 12) ' mendapat nilai 0 - 12

            Select Case cardv
                Case 0
                    card = cardDiamond(val)
                Case 1
                    card = cardHeart(val)
                Case 2
                    card = cardClub(val)
                Case 3
                    card = cardSpade(val)
            End Select

            For d As Integer = 0 To cardNoDeal
                If cardDeal(d) = card Then
                    cardAlreadyLost = True 'card already being deal with 
                End If
            Next
        Loop While cardAlreadyLost = True

        'keluarkan card dari deck jika keep =  false 
        If keep = False Then cardDeal(cardNoDeal) = card
        If keep = False Then cardNoDeal += 1
        Return value(val)
    End Function

    Private Sub Form1_Load(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles MyBase.Load

        cardDiamond(0) = "Diamond Ace"
        cardDiamond(1) = "Diamond Two"
        cardDiamond(2) = "Diamond Three"
        cardDiamond(3) = "Diamond Four"
        cardDiamond(4) = "Diamond Five"
        cardDiamond(5) = "Diamond Six"
        cardDiamond(6) = "Diamond Seven"
        cardDiamond(7) = "Diamond Eight"
        cardDiamond(8) = "Diamond Nine"
        cardDiamond(9) = "Diamond Ten"
        cardDiamond(10) = "Diamond Jack"
        cardDiamond(11) = "Diamond Queen"
        cardDiamond(12) = "Diamond King"

        cardClub(0) = "Club Ace"
        cardClub(1) = "Club Two"
        cardClub(2) = "Club Three"
        cardClub(3) = "Club Four"
        cardClub(4) = "Club Five"
        cardClub(5) = "Club Six"
        cardClub(6) = "Club Seven"
        cardClub(7) = "Club Eight"
        cardClub(8) = "Club Nine"
        cardClub(9) = "Club Ten"
        cardClub(10) = "Club Jack"
        cardClub(11) = "Club Queen"
        cardClub(12) = "Club King"

        cardHeart(0) = "Heart Ace"
        cardHeart(1) = "Heart Two"
        cardHeart(2) = "Heart Three"
        cardHeart(3) = "Heart Four"
        cardHeart(4) = "Heart Five"
        cardHeart(5) = "Heart Six"
        cardHeart(6) = "Heart Seven"
        cardHeart(7) = "Heart Eight"
        cardHeart(8) = "Heart Nine"
        cardHeart(9) = "Heart Ten"
        cardHeart(10) = "Heart Jack"
        cardHeart(11) = "Heart Queen"
        cardHeart(12) = "Heart King"


        cardSpade(0) = "Spade Ace"
        cardSpade(1) = "Spade Two"
        cardSpade(2) = "Spade Three"
        cardSpade(3) = "Spade Four"
        cardSpade(4) = "Spade Five"
        cardSpade(5) = "Spade Six"
        cardSpade(6) = "Spade Seven"
        cardSpade(7) = "Spade Eight"
        cardSpade(8) = "Spade Nine"
        cardSpade(9) = "Spade Ten"
        cardSpade(10) = "Spade Jack"
        cardSpade(11) = "Spade Queen"
        cardSpade(12) = "Spade King"

        value(0) = 1
        value(1) = 2
        value(2) = 3
        value(3) = 4
        value(4) = 5
        value(5) = 6
        value(6) = 7
        value(7) = 8
        value(8) = 9
        value(9) = 10
        value(10) = 10
        value(11) = 10
        value(12) = 10

    End Sub

    Private Sub btnBeginGame_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles btnBeginGame.Click
        TextBox2.Text = String.Empty
        TextBox3.Text = String.Empty
        Dim localcard As String = ""


        'Do
        playerValue = GetValue(localcard, False)
        'Loop While playerValue = -1
        If playerValue = -2 Then
            'MessageBox.Show("No Cards Left")
            Array.Clear(cardDeal, 0, cardDeal.GetLength(0))
            cardNoDeal = 0
            Exit Sub
        End If
        Label1.Text = playerValue
        TextBox1.Text = localcard
        btnBeginGame.Enabled = False
    End Sub

    Private Sub btnAddCard_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles btnAddCard.Click
        Dim card As String = ""
        Dim val As Integer

        val = GetValue(card, False)
        If val = -2 Then
            Array.Clear(cardDeal, 0, cardDeal.GetLength(0))
            cardNoDeal = 0
            Exit Sub
        End If

        playerValue = playerValue + val
        Label1.Text = playerValue
        TextBox2.Text = card & vbCrLf & TextBox2.Text
        If playerValue > 21 Then
            MsgBox("You Lose!!!!")
            btnBeginGame.Enabled = True
        Else
            If playerValue = 21 Then
                MessageBox.Show("You Win!!! BlackJack")
                btnBeginGame.Enabled = True
            End If
        End If
        
    End Sub

    Private Sub btnFold_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles btnFold.Click
        houseValue = 0
        Dim card As String = ""
        Do
            houseValue = houseValue + GetValue(card, False)
            TextBox3.Text = card & vbCrLf & TextBox3.Text
            Label3.Text = houseValue
            If houseValue >= 17 Then
                Dim v As Integer
                v = GetValue(card, True)
                If (v + houseValue) >= 20 Then
                    Exit Do
                End If
            End If
        Loop Until houseValue >= 21
        If playerValue <= 21 Then
            If houseValue > 21 Then
                MsgBox("You Win!!!!")
            Else
                If houseValue > playerValue Then
                    MsgBox("You Lose!!!")
                Else
                    MsgBox("You Win!!!")
                End If
            End If
        End If
        btnBeginGame.Enabled = True
    End Sub

    Private Sub Button1_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles Button1.Click

    End Sub
End Class
