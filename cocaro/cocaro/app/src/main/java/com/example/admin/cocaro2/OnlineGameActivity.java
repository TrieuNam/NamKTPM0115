package com.example.admin.cocaro2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OnlineGameActivity extends AppCompatActivity {

    TextView tvPlayer1,tvPlayer2;

    String playerSession ="";
    String userName ="";
    String otherPlayer = "";
    String loginUID = "";
    String requestType = "", myGameSign = "X";

    int gameState = 0;
    int id_amthanh;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    android.media.SoundPool amthanh =new android.media.SoundPool(1,android.media.AudioManager.STREAM_MUSIC,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);
        userName = getIntent( ).getExtras( ).get("user_name").toString( );
        loginUID = getIntent( ).getExtras( ).get("login_uid").toString( );
        otherPlayer = getIntent( ).getExtras( ).get("other_player").toString( );
        requestType = getIntent( ).getExtras( ).get("request_type").toString( );
        playerSession = getIntent( ).getExtras( ).get("player_session").toString( );

        tvPlayer1 = (TextView) findViewById(R.id.tvPlayer1);
        tvPlayer2 = (TextView) findViewById(R.id.tvPlayer2);
        id_amthanh = amthanh.load(this,R.raw.multimedia,1);

        gameState = 1;
        if (requestType.equals("From")) {
            myGameSign = "0";
            tvPlayer1.setText("Tới lượt bạn ");
            tvPlayer2.setText("Tới lượt bạn");
            myRef.child("playing").child(playerSession).child("turn").setValue(userName);
        } else {
            myGameSign = "X";
            tvPlayer1.setText(otherPlayer + "\'s turn");
            tvPlayer2.setText(otherPlayer + "\'s turn");

            myRef.child("playing").child(playerSession).child("turn").setValue(otherPlayer);

        }
        myRef.child("playing").child(playerSession).child("turn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    String value = (String) dataSnapshot.getValue();
                    if(value.equals(userName)) {
                        tvPlayer1.setText("Tới Lượt Bạn");
                        tvPlayer2.setText("Tới Lượt Bạn");
                        setEnableClick(true);
                        activePlayer = 1;
                    }else if(value.equals(otherPlayer)){
                        tvPlayer1.setText(otherPlayer + "\'s turn");
                        tvPlayer2.setText(otherPlayer + "\'s turn");
                        setEnableClick(false);
                        activePlayer = 2;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.child("playing").child(playerSession).child("game")

                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            Player1.clear();
                            Player2.clear();
                            activePlayer = 2;
                            HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                            if(map != null){
                                String value = "";
                                String firstPlayer = userName;
                                for(String key:map.keySet()){
                                    value = (String) map.get(key);
                                    if(value.equals(userName)){

                                        activePlayer = 2;
                                    }else{
                                        activePlayer = 1;
                                    }
                                    firstPlayer = value;
                                    String[] splitID = key.split(":");
                                    OtherPlayer(Integer.parseInt(splitID[1]));
                                }
                            }
                        }catch (Exception e){

                            e.printStackTrace();
                        }
                    }

                    @Override

                    public void onCancelled(DatabaseError error) {

                    }

                });

    }

    int activePlayer = 1;
    ArrayList<Integer> Player1 = new ArrayList<Integer>();
    ArrayList<Integer> Player2 = new ArrayList<Integer>();
    void  PlayGame(int selectedBlock,ImageView selectedImage){
        if(gameState == 1) {
            if (activePlayer == 1) {
                selectedImage.setImageResource(R.drawable.tt_x);
                Player1.add(selectedBlock);
            }else if (activePlayer == 2) {
                selectedImage.setImageResource(R.drawable.tt_o);
                Player2.add(selectedBlock);
            }
            selectedImage.setEnabled(false);
            CheckWinner();
        }
    }



    void CheckWinner(){
        int winner = 0;
        /********* for Player 1 *********/
        /********* Hàng 1 *********/
        if(Player1.contains(1) && Player1.contains(2) && Player1.contains(3)&& Player1.contains(4)&& Player1.contains(5)){ winner = 1; }
        if(Player1.contains(2) && Player1.contains(3) && Player1.contains(4)&& Player1.contains(5)&& Player1.contains(6)){ winner = 1; }
        if(Player1.contains(3) && Player1.contains(4) && Player1.contains(5)&& Player1.contains(6)&& Player1.contains(7)){ winner = 1; }
        if(Player1.contains(4) && Player1.contains(5) && Player1.contains(6)&& Player1.contains(7)&& Player1.contains(8)){ winner = 1; }
        if(Player1.contains(5) && Player1.contains(6) && Player1.contains(7)&& Player1.contains(8)&& Player1.contains(9)){ winner = 1; }
        /********* Hàng 2 *********/
        if(Player1.contains(10) && Player1.contains(11) && Player1.contains(12)&& Player1.contains(13)&& Player1.contains(14)){ winner = 1; }
        if(Player1.contains(11) && Player1.contains(12) && Player1.contains(13)&& Player1.contains(14)&& Player1.contains(15)){ winner = 1; }
        if(Player1.contains(12) && Player1.contains(13) && Player1.contains(14)&& Player1.contains(15)&& Player1.contains(16)){ winner = 1; }
        if(Player1.contains(13) && Player1.contains(14) && Player1.contains(15)&& Player1.contains(16)&& Player1.contains(17)){ winner = 1; }
        if(Player1.contains(14) && Player1.contains(15) && Player1.contains(16)&& Player1.contains(17)&& Player1.contains(18)){ winner = 1; }
        /********* Hàng 3 *********/
        if(Player1.contains(19) && Player1.contains(20) && Player1.contains(21)&& Player1.contains(22)&& Player1.contains(23)){ winner = 1; }
        if(Player1.contains(20) && Player1.contains(21) && Player1.contains(22)&& Player1.contains(23)&& Player1.contains(24)){ winner = 1; }
        if(Player1.contains(21) && Player1.contains(22) && Player1.contains(23)&& Player1.contains(24)&& Player1.contains(25)){ winner = 1; }
        if(Player1.contains(22) && Player1.contains(23) && Player1.contains(24)&& Player1.contains(25)&& Player1.contains(26)){ winner = 1; }
        if(Player1.contains(23) && Player1.contains(24) && Player1.contains(25)&& Player1.contains(26)&& Player1.contains(27)){ winner = 1; }
        /********* Hàng 4 *********/
        if(Player1.contains(28) && Player1.contains(29) && Player1.contains(30)&& Player1.contains(31)&& Player1.contains(32)){ winner = 1; }
        if(Player1.contains(29) && Player1.contains(30) && Player1.contains(31)&& Player1.contains(32)&& Player1.contains(33)){ winner = 1; }
        if(Player1.contains(30) && Player1.contains(31) && Player1.contains(32)&& Player1.contains(33)&& Player1.contains(34)){ winner = 1; }
        if(Player1.contains(31) && Player1.contains(32) && Player1.contains(33)&& Player1.contains(34)&& Player1.contains(35)){ winner = 1; }
        if(Player1.contains(32) && Player1.contains(33) && Player1.contains(34)&& Player1.contains(35)&& Player1.contains(36)){ winner = 1; }
        /********* Hàng 5 *********/
        if(Player1.contains(37) && Player1.contains(38) && Player1.contains(39)&& Player1.contains(40)&& Player1.contains(41)){ winner = 1; }
        if(Player1.contains(38) && Player1.contains(39) && Player1.contains(40)&& Player1.contains(41)&& Player1.contains(42)){ winner = 1; }
        if(Player1.contains(39) && Player1.contains(40) && Player1.contains(41)&& Player1.contains(42)&& Player1.contains(43)){ winner = 1; }
        if(Player1.contains(40) && Player1.contains(41) && Player1.contains(42)&& Player1.contains(43)&& Player1.contains(44)){ winner = 1; }
        if(Player1.contains(41) && Player1.contains(42) && Player1.contains(43)&& Player1.contains(44)&& Player1.contains(45)){ winner = 1; }
        /********* Hàng 6 *********/
        if(Player1.contains(46) && Player1.contains(47) && Player1.contains(48)&& Player1.contains(49)&& Player1.contains(50)){ winner = 1; }
        if(Player1.contains(47) && Player1.contains(48) && Player1.contains(49)&& Player1.contains(50)&& Player1.contains(51)){ winner = 1; }
        if(Player1.contains(48) && Player1.contains(49) && Player1.contains(50)&& Player1.contains(51)&& Player1.contains(52)){ winner = 1; }
        if(Player1.contains(49) && Player1.contains(50) && Player1.contains(51)&& Player1.contains(52)&& Player1.contains(53)){ winner = 1; }
        if(Player1.contains(50) && Player1.contains(51) && Player1.contains(52)&& Player1.contains(53)&& Player1.contains(54)){ winner = 1; }
        /********* Hàng 7 *********/
        if(Player1.contains(55) && Player1.contains(56) && Player1.contains(57)&& Player1.contains(58)&& Player1.contains(59)){ winner = 1; }
        if(Player1.contains(56) && Player1.contains(57) && Player1.contains(58)&& Player1.contains(59)&& Player1.contains(60)){ winner = 1; }
        if(Player1.contains(57) && Player1.contains(58) && Player1.contains(59)&& Player1.contains(60)&& Player1.contains(61)){ winner = 1; }
        if(Player1.contains(58) && Player1.contains(59) && Player1.contains(60)&& Player1.contains(61)&& Player1.contains(62)){ winner = 1; }
        if(Player1.contains(59) && Player1.contains(60) && Player1.contains(61)&& Player1.contains(62)&& Player1.contains(63)){ winner = 1; }
        /********* Hàng 8 *********/
        if(Player1.contains(64) && Player1.contains(65) && Player1.contains(66)&& Player1.contains(67)&& Player1.contains(68)){ winner = 1; }
        if(Player1.contains(65) && Player1.contains(66) && Player1.contains(67)&& Player1.contains(68)&& Player1.contains(69)){ winner = 1; }
        if(Player1.contains(66) && Player1.contains(67) && Player1.contains(68)&& Player1.contains(69)&& Player1.contains(70)){ winner = 1; }
        if(Player1.contains(67) && Player1.contains(68) && Player1.contains(69)&& Player1.contains(70)&& Player1.contains(71)){ winner = 1; }
        if(Player1.contains(68) && Player1.contains(69) && Player1.contains(70)&& Player1.contains(71)&& Player1.contains(72)){ winner = 1; }
        /********* Hàng 9 *********/
        if(Player1.contains(73) && Player1.contains(74) && Player1.contains(75)&& Player1.contains(76)&& Player1.contains(77)){ winner = 1; }
        if(Player1.contains(74) && Player1.contains(75) && Player1.contains(76)&& Player1.contains(77)&& Player1.contains(78)){ winner = 1; }
        if(Player1.contains(75) && Player1.contains(76) && Player1.contains(77)&& Player1.contains(78)&& Player1.contains(79)){ winner = 1; }
        if(Player1.contains(76) && Player1.contains(77) && Player1.contains(78)&& Player1.contains(79)&& Player1.contains(80)){ winner = 1; }
        if(Player1.contains(77) && Player1.contains(78) && Player1.contains(79)&& Player1.contains(80)&& Player1.contains(81)){ winner = 1; }
        /********* Dọc 1 *********/
        if(Player1.contains(1) && Player1.contains(10) && Player1.contains(19)&& Player1.contains(28)&& Player1.contains(37)){ winner = 1; }
        if(Player1.contains(10) && Player1.contains(19) && Player1.contains(28)&& Player1.contains(37)&& Player1.contains(46)){ winner = 1; }
        if(Player1.contains(19) && Player1.contains(28) && Player1.contains(37)&& Player1.contains(46)&& Player1.contains(55)){ winner = 1; }
        if(Player1.contains(28) && Player1.contains(37) && Player1.contains(46)&& Player1.contains(55)&& Player1.contains(64)){ winner = 1; }
        if(Player1.contains(37) && Player1.contains(46) && Player1.contains(55)&& Player1.contains(64)&& Player1.contains(73)){ winner = 1; }
        /********* Dọc 2 *********/
        if(Player1.contains(2) && Player1.contains(11) && Player1.contains(20)&& Player1.contains(29)&& Player1.contains(38)){ winner = 1; }
        if(Player1.contains(11) && Player1.contains(20) && Player1.contains(29)&& Player1.contains(38)&& Player1.contains(47)){ winner = 1; }
        if(Player1.contains(20) && Player1.contains(29) && Player1.contains(38)&& Player1.contains(47)&& Player1.contains(56)){ winner = 1; }
        if(Player1.contains(29) && Player1.contains(38) && Player1.contains(47)&& Player1.contains(56)&& Player1.contains(65)){ winner = 1; }
        if(Player1.contains(38) && Player1.contains(47) && Player1.contains(56)&& Player1.contains(65)&& Player1.contains(74)){ winner = 1; }
        /********* Dọc 3 *********/
        if(Player1.contains(3) && Player1.contains(12) && Player1.contains(21)&& Player1.contains(30)&& Player1.contains(39)){ winner = 1; }
        if(Player1.contains(12) && Player1.contains(21) && Player1.contains(30)&& Player1.contains(39)&& Player1.contains(48)){ winner = 1; }
        if(Player1.contains(21) && Player1.contains(30) && Player1.contains(39)&& Player1.contains(48)&& Player1.contains(57)){ winner = 1; }
        if(Player1.contains(30) && Player1.contains(39) && Player1.contains(48)&& Player1.contains(57)&& Player1.contains(66)){ winner = 1; }
        if(Player1.contains(39) && Player1.contains(48) && Player1.contains(57)&& Player1.contains(66)&& Player1.contains(75)){ winner = 1; }
        /********* Dọc 4 *********/
        if(Player1.contains(4) && Player1.contains(13) && Player1.contains(22)&& Player1.contains(31)&& Player1.contains(40)){ winner = 1; }
        if(Player1.contains(13) && Player1.contains(22) && Player1.contains(31)&& Player1.contains(40)&& Player1.contains(49)){ winner = 1; }
        if(Player1.contains(22) && Player1.contains(31) && Player1.contains(40)&& Player1.contains(49)&& Player1.contains(58)){ winner = 1; }
        if(Player1.contains(31) && Player1.contains(40) && Player1.contains(49)&& Player1.contains(58)&& Player1.contains(67)){ winner = 1; }
        if(Player1.contains(40) && Player1.contains(49) && Player1.contains(58)&& Player1.contains(67)&& Player1.contains(76)){ winner = 1; }
        /********* Dọc 5 *********/
        if(Player1.contains(5) && Player1.contains(14) && Player1.contains(23)&& Player1.contains(32)&& Player1.contains(41)){ winner = 1; }
        if(Player1.contains(14) && Player1.contains(23) && Player1.contains(32)&& Player1.contains(41)&& Player1.contains(50)){ winner = 1; }
        if(Player1.contains(23) && Player1.contains(32) && Player1.contains(41)&& Player1.contains(50)&& Player1.contains(59)){ winner = 1; }
        if(Player1.contains(32) && Player1.contains(41) && Player1.contains(50)&& Player1.contains(59)&& Player1.contains(68)){ winner = 1; }
        if(Player1.contains(41) && Player1.contains(50) && Player1.contains(59)&& Player1.contains(68)&& Player1.contains(77)){ winner = 1; }

        /********* Dọc 6 *********/
        if(Player1.contains(6) && Player1.contains(15) && Player1.contains(24)&& Player1.contains(33)&& Player1.contains(42)){ winner = 1; }
        if(Player1.contains(15) && Player1.contains(24) && Player1.contains(33)&& Player1.contains(42)&& Player1.contains(51)){ winner = 1; }
        if(Player1.contains(24) && Player1.contains(33) && Player1.contains(42)&& Player1.contains(51)&& Player1.contains(60)){ winner = 1; }
        if(Player1.contains(33) && Player1.contains(42) && Player1.contains(51)&& Player1.contains(60)&& Player1.contains(69)){ winner = 1; }
        if(Player1.contains(42) && Player1.contains(51) && Player1.contains(60)&& Player1.contains(69)&& Player1.contains(78)){ winner = 1; }
        /********* Dọc 7 *********/
        if(Player1.contains(7) && Player1.contains(16) && Player1.contains(25)&& Player1.contains(34)&& Player1.contains(43)){ winner = 1; }
        if(Player1.contains(16) && Player1.contains(25) && Player1.contains(34)&& Player1.contains(43)&& Player1.contains(52)){ winner = 1; }
        if(Player1.contains(25) && Player1.contains(34) && Player1.contains(43)&& Player1.contains(52)&& Player1.contains(61)){ winner = 1; }
        if(Player1.contains(34) && Player1.contains(43) && Player1.contains(52)&& Player1.contains(61)&& Player1.contains(70)){ winner = 1; }
        if(Player1.contains(43) && Player1.contains(52) && Player1.contains(61)&& Player1.contains(70)&& Player1.contains(79)){ winner = 1; }
/********* Dọc 8 *********/
        if(Player1.contains(8) && Player1.contains(17) && Player1.contains(26)&& Player1.contains(35)&& Player1.contains(44)){ winner = 1; }
        if(Player1.contains(17) && Player1.contains(26) && Player1.contains(35)&& Player1.contains(44)&& Player1.contains(53)){ winner = 1; }
        if(Player1.contains(26) && Player1.contains(35) && Player1.contains(44)&& Player1.contains(54)&& Player1.contains(62)){ winner = 1; }
        if(Player1.contains(35) && Player1.contains(44) && Player1.contains(53)&& Player1.contains(62)&& Player1.contains(71)){ winner = 1; }
        if(Player1.contains(44) && Player1.contains(53) && Player1.contains(62)&& Player1.contains(71)&& Player1.contains(80)){ winner = 1; }
/********* Dọc 8 *********/
        if(Player1.contains(9) && Player1.contains(18) && Player1.contains(27)&& Player1.contains(36)&& Player1.contains(45)){ winner = 1; }
        if(Player1.contains(18) && Player1.contains(27) && Player1.contains(36)&& Player1.contains(45)&& Player1.contains(54)){ winner = 1; }
        if(Player1.contains(27) && Player1.contains(36) && Player1.contains(45)&& Player1.contains(54)&& Player1.contains(63)){ winner = 1; }
        if(Player1.contains(26) && Player1.contains(45) && Player1.contains(54)&& Player1.contains(63)&& Player1.contains(72)){ winner = 1; }
        if(Player1.contains(45) && Player1.contains(54) && Player1.contains(63)&& Player1.contains(72)&& Player1.contains(81)){ winner = 1; }

/********* cheo 1 *********/
        if(Player1.contains(37) && Player1.contains(47) && Player1.contains(57)&& Player1.contains(67)&& Player1.contains(77)){ winner = 1; }
        if(Player1.contains(28) && Player1.contains(38) && Player1.contains(48)&& Player1.contains(58)&& Player1.contains(68)){ winner = 1; }
        if(Player1.contains(38) && Player1.contains(48) && Player1.contains(58)&& Player1.contains(68)&& Player1.contains(78)){ winner = 1; }
        if(Player1.contains(19) && Player1.contains(29) && Player1.contains(39)&& Player1.contains(49)&& Player1.contains(59)){ winner = 1; }
        if(Player1.contains(29) && Player1.contains(39) && Player1.contains(49)&& Player1.contains(59)&& Player1.contains(69)){ winner = 1; }
        if(Player1.contains(39) && Player1.contains(49) && Player1.contains(59)&& Player1.contains(69)&& Player1.contains(79)){ winner = 1; }
        if(Player1.contains(10) && Player1.contains(20) && Player1.contains(30)&& Player1.contains(40)&& Player1.contains(50)){ winner = 1; }
        if(Player1.contains(20) && Player1.contains(30) && Player1.contains(40)&& Player1.contains(50)&& Player1.contains(60)){ winner = 1; }
        if(Player1.contains(30) && Player1.contains(40) && Player1.contains(60)&& Player1.contains(70)&& Player1.contains(80)){ winner = 1; }
        if(Player1.contains(1) && Player1.contains(11) && Player1.contains(21)&& Player1.contains(31)&& Player1.contains(41)){ winner = 1; }
        if(Player1.contains(11) && Player1.contains(21) && Player1.contains(31)&& Player1.contains(41)&& Player1.contains(51)){ winner = 1; }
        if(Player1.contains(21) && Player1.contains(31) && Player1.contains(41)&& Player1.contains(51)&& Player1.contains(61)){ winner = 1; }
        if(Player1.contains(31) && Player1.contains(41) && Player1.contains(51)&& Player1.contains(61)&& Player1.contains(71)){ winner = 1; }
        if(Player1.contains(41) && Player1.contains(51) && Player1.contains(61)&& Player1.contains(71)&& Player1.contains(81)){ winner = 1; }
        if(Player1.contains(2) && Player1.contains(12) && Player1.contains(22)&& Player1.contains(32)&& Player1.contains(42)){ winner = 1; }
        if(Player1.contains(12) && Player1.contains(22) && Player1.contains(32)&& Player1.contains(42)&& Player1.contains(52)){ winner = 1; }
        if(Player1.contains(22) && Player1.contains(32) && Player1.contains(42)&& Player1.contains(52)&& Player1.contains(62)){ winner = 1; }
        if(Player1.contains(32) && Player1.contains(42) && Player1.contains(52)&& Player1.contains(62)&& Player1.contains(72)){ winner = 1; }
        if(Player1.contains(3) && Player1.contains(13) && Player1.contains(23)&& Player1.contains(33)&& Player1.contains(43)){ winner = 1; }
        if(Player1.contains(13) && Player1.contains(23) && Player1.contains(33)&& Player1.contains(43)&& Player1.contains(53)){ winner = 1; }
        if(Player1.contains(23) && Player1.contains(33) && Player1.contains(43)&& Player1.contains(53)&& Player1.contains(63)){ winner = 1; }
        if(Player1.contains(4) && Player1.contains(14) && Player1.contains(24)&& Player1.contains(34)&& Player1.contains(44)){ winner = 1; }
        if(Player1.contains(14) && Player1.contains(24) && Player1.contains(34)&& Player1.contains(44)&& Player1.contains(54)){ winner = 1; }
        if(Player1.contains(5) && Player1.contains(15) && Player1.contains(25)&& Player1.contains(35)&& Player1.contains(45)){ winner = 1; }
        /********* cheo 2 *********/
        if(Player1.contains(5) && Player1.contains(13) && Player1.contains(21)&& Player1.contains(29)&& Player1.contains(37)){ winner = 1; }
        if(Player1.contains(6) && Player1.contains(14) && Player1.contains(22)&& Player1.contains(30)&& Player1.contains(38)){ winner = 1; }
        if(Player1.contains(14) && Player1.contains(22) && Player1.contains(30)&& Player1.contains(38)&& Player1.contains(46)){ winner = 1; }
        if(Player1.contains(7) && Player1.contains(15) && Player1.contains(23)&& Player1.contains(31)&& Player1.contains(39)){ winner = 1; }
        if(Player1.contains(15) && Player1.contains(23) && Player1.contains(31)&& Player1.contains(39)&& Player1.contains(47)){ winner = 1; }
        if(Player1.contains(23) && Player1.contains(31) && Player1.contains(39)&& Player1.contains(47)&& Player1.contains(55)){ winner = 1; }

        if(Player1.contains(8) && Player1.contains(16) && Player1.contains(24)&& Player1.contains(32)&& Player1.contains(40)){ winner = 1; }
        if(Player1.contains(16) && Player1.contains(24) && Player1.contains(32)&& Player1.contains(40)&& Player1.contains(48)){ winner = 1; }
        if(Player1.contains(24) && Player1.contains(32) && Player1.contains(40)&& Player1.contains(48)&& Player1.contains(56)){ winner = 1; }
        if(Player1.contains(32) && Player1.contains(40) && Player1.contains(48)&& Player1.contains(56)&& Player1.contains(64)){ winner = 1; }

        if(Player1.contains(9) && Player1.contains(17) && Player1.contains(25)&& Player1.contains(33)&& Player1.contains(41)){ winner = 1; }
        if(Player1.contains(17) && Player1.contains(25) && Player1.contains(33)&& Player1.contains(41)&& Player1.contains(49)){ winner = 1; }
        if(Player1.contains(25) && Player1.contains(33) && Player1.contains(41)&& Player1.contains(49)&& Player1.contains(57)){ winner = 1; }
        if(Player1.contains(33) && Player1.contains(41) && Player1.contains(49)&& Player1.contains(57)&& Player1.contains(65)){ winner = 1; }
        if(Player1.contains(41) && Player1.contains(49) && Player1.contains(57)&& Player1.contains(65)&& Player1.contains(73)){ winner = 1; }

        if(Player1.contains(18) && Player1.contains(26) && Player1.contains(34)&& Player1.contains(42)&& Player1.contains(50)){ winner = 1; }
        if(Player1.contains(26) && Player1.contains(34) && Player1.contains(42)&& Player1.contains(50)&& Player1.contains(58)){ winner = 1; }
        if(Player1.contains(34) && Player1.contains(42) && Player1.contains(50)&& Player1.contains(58)&& Player1.contains(66)){ winner = 1; }
        if(Player1.contains(42) && Player1.contains(50) && Player1.contains(58)&& Player1.contains(66)&& Player1.contains(74)){ winner = 1; }

        if(Player1.contains(27) && Player1.contains(35) && Player1.contains(43)&& Player1.contains(51)&& Player1.contains(59)){ winner = 1; }
        if(Player1.contains(35) && Player1.contains(43) && Player1.contains(51)&& Player1.contains(59)&& Player1.contains(67)){ winner = 1; }
        if(Player1.contains(43) && Player1.contains(51) && Player1.contains(59)&& Player1.contains(67)&& Player1.contains(75)){ winner = 1; }

        if(Player1.contains(36) && Player1.contains(44) && Player1.contains(52)&& Player1.contains(60)&& Player1.contains(61)){ winner = 1; }
        if(Player1.contains(44) && Player1.contains(52) && Player1.contains(60)&& Player1.contains(68)&& Player1.contains(76)){ winner = 1; }

        if(Player1.contains(45) && Player1.contains(53) && Player1.contains(61)&& Player1.contains(69)&& Player1.contains(77)){ winner = 1; }


        /********* for Player 2 *********/
        /********* Hàng 1 *********/
        if(Player1.contains(1) && Player1.contains(2) && Player1.contains(3)&& Player1.contains(4)&& Player1.contains(5)){ winner = 2; }
        if(Player1.contains(2) && Player1.contains(3) && Player1.contains(4)&& Player1.contains(5)&& Player1.contains(6)){ winner = 2; }
        if(Player1.contains(3) && Player1.contains(4) && Player1.contains(5)&& Player1.contains(6)&& Player1.contains(7)){ winner = 2; }
        if(Player1.contains(4) && Player1.contains(5) && Player1.contains(6)&& Player1.contains(7)&& Player1.contains(8)){ winner = 2; }
        if(Player1.contains(5) && Player1.contains(6) && Player1.contains(7)&& Player1.contains(8)&& Player1.contains(9)){ winner = 2; }
        /********* Hàng 2 *********/
        if(Player1.contains(10) && Player1.contains(11) && Player1.contains(12)&& Player1.contains(13)&& Player1.contains(14)){ winner = 2; }
        if(Player1.contains(11) && Player1.contains(12) && Player1.contains(13)&& Player1.contains(14)&& Player1.contains(15)){ winner = 2; }
        if(Player1.contains(12) && Player1.contains(13) && Player1.contains(14)&& Player1.contains(15)&& Player1.contains(16)){ winner = 2; }
        if(Player1.contains(13) && Player1.contains(14) && Player1.contains(15)&& Player1.contains(16)&& Player1.contains(17)){ winner = 2; }
        if(Player1.contains(14) && Player1.contains(15) && Player1.contains(16)&& Player1.contains(17)&& Player1.contains(18)){ winner = 2; }
        /********* Hàng 3 *********/
        if(Player1.contains(19) && Player1.contains(20) && Player1.contains(21)&& Player1.contains(22)&& Player1.contains(23)){ winner = 2; }
        if(Player1.contains(20) && Player1.contains(21) && Player1.contains(22)&& Player1.contains(23)&& Player1.contains(24)){ winner = 2; }
        if(Player1.contains(21) && Player1.contains(22) && Player1.contains(23)&& Player1.contains(24)&& Player1.contains(25)){ winner = 2; }
        if(Player1.contains(22) && Player1.contains(23) && Player1.contains(24)&& Player1.contains(25)&& Player1.contains(26)){ winner = 2; }
        if(Player1.contains(23) && Player1.contains(24) && Player1.contains(25)&& Player1.contains(26)&& Player1.contains(27)){ winner = 2; }
        /********* Hàng 4 *********/
        if(Player1.contains(28) && Player1.contains(29) && Player1.contains(30)&& Player1.contains(31)&& Player1.contains(32)){ winner = 2; }
        if(Player1.contains(29) && Player1.contains(30) && Player1.contains(31)&& Player1.contains(32)&& Player1.contains(33)){ winner = 2; }
        if(Player1.contains(30) && Player1.contains(31) && Player1.contains(32)&& Player1.contains(33)&& Player1.contains(34)){ winner = 2; }
        if(Player1.contains(31) && Player1.contains(32) && Player1.contains(33)&& Player1.contains(34)&& Player1.contains(35)){ winner = 2; }
        if(Player1.contains(32) && Player1.contains(33) && Player1.contains(34)&& Player1.contains(35)&& Player1.contains(36)){ winner = 2; }
        /********* Hàng 5 *********/
        if(Player1.contains(37) && Player1.contains(38) && Player1.contains(39)&& Player1.contains(40)&& Player1.contains(41)){ winner = 2; }
        if(Player1.contains(38) && Player1.contains(39) && Player1.contains(40)&& Player1.contains(41)&& Player1.contains(42)){ winner = 2; }
        if(Player1.contains(39) && Player1.contains(40) && Player1.contains(41)&& Player1.contains(42)&& Player1.contains(43)){ winner = 2; }
        if(Player1.contains(40) && Player1.contains(41) && Player1.contains(42)&& Player1.contains(43)&& Player1.contains(44)){ winner = 2; }
        if(Player1.contains(41) && Player1.contains(42) && Player1.contains(43)&& Player1.contains(44)&& Player1.contains(45)){ winner = 2; }
        /********* Hàng 6 *********/
        if(Player1.contains(46) && Player1.contains(47) && Player1.contains(48)&& Player1.contains(49)&& Player1.contains(50)){ winner = 2; }
        if(Player1.contains(47) && Player1.contains(48) && Player1.contains(49)&& Player1.contains(50)&& Player1.contains(51)){ winner = 2; }
        if(Player1.contains(48) && Player1.contains(49) && Player1.contains(50)&& Player1.contains(51)&& Player1.contains(52)){ winner = 2; }
        if(Player1.contains(49) && Player1.contains(50) && Player1.contains(51)&& Player1.contains(52)&& Player1.contains(53)){ winner = 2; }
        if(Player1.contains(50) && Player1.contains(51) && Player1.contains(52)&& Player1.contains(53)&& Player1.contains(54)){ winner = 2; }
        /********* Hàng 7 *********/
        if(Player1.contains(55) && Player1.contains(56) && Player1.contains(57)&& Player1.contains(58)&& Player1.contains(59)){ winner = 2; }
        if(Player1.contains(56) && Player1.contains(57) && Player1.contains(58)&& Player1.contains(59)&& Player1.contains(60)){ winner = 2; }
        if(Player1.contains(57) && Player1.contains(58) && Player1.contains(59)&& Player1.contains(60)&& Player1.contains(61)){ winner = 2; }
        if(Player1.contains(58) && Player1.contains(59) && Player1.contains(60)&& Player1.contains(61)&& Player1.contains(62)){ winner = 2; }
        if(Player1.contains(59) && Player1.contains(60) && Player1.contains(61)&& Player1.contains(62)&& Player1.contains(63)){ winner = 2; }
        /********* Hàng 8 *********/
        if(Player1.contains(64) && Player1.contains(65) && Player1.contains(66)&& Player1.contains(67)&& Player1.contains(68)){ winner = 2; }
        if(Player1.contains(65) && Player1.contains(66) && Player1.contains(67)&& Player1.contains(68)&& Player1.contains(69)){ winner = 2; }
        if(Player1.contains(66) && Player1.contains(67) && Player1.contains(68)&& Player1.contains(69)&& Player1.contains(70)){ winner = 2; }
        if(Player1.contains(67) && Player1.contains(68) && Player1.contains(69)&& Player1.contains(70)&& Player1.contains(71)){ winner = 2; }
        if(Player1.contains(68) && Player1.contains(69) && Player1.contains(70)&& Player1.contains(71)&& Player1.contains(72)){ winner = 2; }
        /********* Hàng 9 *********/
        if(Player1.contains(73) && Player1.contains(74) && Player1.contains(75)&& Player1.contains(76)&& Player1.contains(77)){ winner = 2; }
        if(Player1.contains(74) && Player1.contains(75) && Player1.contains(76)&& Player1.contains(77)&& Player1.contains(78)){ winner = 2; }
        if(Player1.contains(75) && Player1.contains(76) && Player1.contains(77)&& Player1.contains(78)&& Player1.contains(79)){ winner = 2; }
        if(Player1.contains(76) && Player1.contains(77) && Player1.contains(78)&& Player1.contains(79)&& Player1.contains(80)){ winner = 2; }
        if(Player1.contains(77) && Player1.contains(78) && Player1.contains(79)&& Player1.contains(80)&& Player1.contains(81)){ winner = 2; }
        /********* Dọc 1 *********/
        if(Player1.contains(1) && Player1.contains(10) && Player1.contains(19)&& Player1.contains(28)&& Player1.contains(37)){ winner = 2;}
        if(Player1.contains(10) && Player1.contains(19) && Player1.contains(28)&& Player1.contains(37)&& Player1.contains(46)){ winner =2; }
        if(Player1.contains(19) && Player1.contains(28) && Player1.contains(37)&& Player1.contains(46)&& Player1.contains(55)){ winner = 2; }
        if(Player1.contains(28) && Player1.contains(37) && Player1.contains(46)&& Player1.contains(55)&& Player1.contains(64)){ winner = 2; }
        if(Player1.contains(37) && Player1.contains(46) && Player1.contains(55)&& Player1.contains(64)&& Player1.contains(73)){ winner = 2; }
        /********* Dọc 2 *********/
        if(Player1.contains(2) && Player1.contains(11) && Player1.contains(20)&& Player1.contains(29)&& Player1.contains(38)){ winner = 2; }
        if(Player1.contains(11) && Player1.contains(20) && Player1.contains(29)&& Player1.contains(38)&& Player1.contains(47)){ winner = 2; }
        if(Player1.contains(20) && Player1.contains(29) && Player1.contains(38)&& Player1.contains(47)&& Player1.contains(56)){ winner = 2; }
        if(Player1.contains(29) && Player1.contains(38) && Player1.contains(47)&& Player1.contains(56)&& Player1.contains(65)){ winner = 2; }
        if(Player1.contains(38) && Player1.contains(47) && Player1.contains(56)&& Player1.contains(65)&& Player1.contains(74)){ winner = 2 ;}
        /********* Dọc 3 *********/
        if(Player1.contains(3) && Player1.contains(12) && Player1.contains(21)&& Player1.contains(30)&& Player1.contains(39)){ winner = 2; }
        if(Player1.contains(12) && Player1.contains(21) && Player1.contains(30)&& Player1.contains(39)&& Player1.contains(48)){ winner = 2; }
        if(Player1.contains(21) && Player1.contains(30) && Player1.contains(39)&& Player1.contains(48)&& Player1.contains(57)){ winner = 2; }
        if(Player1.contains(30) && Player1.contains(39) && Player1.contains(48)&& Player1.contains(57)&& Player1.contains(66)){ winner = 2; }
        if(Player1.contains(39) && Player1.contains(48) && Player1.contains(57)&& Player1.contains(66)&& Player1.contains(75)){ winner = 2; }
        /********* Dọc 4 *********/
        if(Player1.contains(4) && Player1.contains(13) && Player1.contains(22)&& Player1.contains(31)&& Player1.contains(40)){ winner = 2; }
        if(Player1.contains(13) && Player1.contains(22) && Player1.contains(31)&& Player1.contains(40)&& Player1.contains(49)){ winner = 2; }
        if(Player1.contains(22) && Player1.contains(31) && Player1.contains(40)&& Player1.contains(49)&& Player1.contains(58)){ winner = 2; }
        if(Player1.contains(31) && Player1.contains(40) && Player1.contains(49)&& Player1.contains(58)&& Player1.contains(67)){ winner = 2; }
        if(Player1.contains(40) && Player1.contains(49) && Player1.contains(58)&& Player1.contains(67)&& Player1.contains(76)){ winner = 2; }
        /********* Dọc 5 *********/
        if(Player1.contains(5) && Player1.contains(14) && Player1.contains(23)&& Player1.contains(32)&& Player1.contains(41)){ winner = 2; }
        if(Player1.contains(14) && Player1.contains(23) && Player1.contains(32)&& Player1.contains(41)&& Player1.contains(50)){ winner = 2; }
        if(Player1.contains(23) && Player1.contains(32) && Player1.contains(41)&& Player1.contains(50)&& Player1.contains(59)){ winner = 2; }
        if(Player1.contains(32) && Player1.contains(41) && Player1.contains(50)&& Player1.contains(59)&& Player1.contains(68)){ winner = 2; }
        if(Player1.contains(41) && Player1.contains(50) && Player1.contains(59)&& Player1.contains(68)&& Player1.contains(77)){ winner = 2; }

        /********* Dọc 6 *********/
        if(Player1.contains(6) && Player1.contains(15) && Player1.contains(24)&& Player1.contains(33)&& Player1.contains(42)){ winner = 2; }
        if(Player1.contains(15) && Player1.contains(24) && Player1.contains(33)&& Player1.contains(42)&& Player1.contains(51)){ winner = 2; }
        if(Player1.contains(24) && Player1.contains(33) && Player1.contains(42)&& Player1.contains(51)&& Player1.contains(60)){ winner = 2; }
        if(Player1.contains(33) && Player1.contains(42) && Player1.contains(51)&& Player1.contains(60)&& Player1.contains(69)){ winner = 2; }
        if(Player1.contains(42) && Player1.contains(51) && Player1.contains(60)&& Player1.contains(69)&& Player1.contains(78)){ winner = 2; }
        /********* Dọc 7 *********/
        if(Player1.contains(7) && Player1.contains(16) && Player1.contains(25)&& Player1.contains(34)&& Player1.contains(43)){ winner = 2; }
        if(Player1.contains(16) && Player1.contains(25) && Player1.contains(34)&& Player1.contains(43)&& Player1.contains(52)){ winner = 2; }
        if(Player1.contains(25) && Player1.contains(34) && Player1.contains(43)&& Player1.contains(52)&& Player1.contains(61)){ winner = 2; }
        if(Player1.contains(34) && Player1.contains(43) && Player1.contains(52)&& Player1.contains(61)&& Player1.contains(70)){ winner = 2; }
        if(Player1.contains(43) && Player1.contains(52) && Player1.contains(61)&& Player1.contains(70)&& Player1.contains(79)){ winner = 2; }
/********* Dọc 8 *********/
        if(Player1.contains(8) && Player1.contains(17) && Player1.contains(26)&& Player1.contains(35)&& Player1.contains(44)){ winner = 2; }
        if(Player1.contains(17) && Player1.contains(26) && Player1.contains(35)&& Player1.contains(44)&& Player1.contains(53)){ winner = 2; }
        if(Player1.contains(26) && Player1.contains(35) && Player1.contains(44)&& Player1.contains(54)&& Player1.contains(62)){ winner = 2; }
        if(Player1.contains(35) && Player1.contains(44) && Player1.contains(53)&& Player1.contains(62)&& Player1.contains(71)){ winner = 2; }
        if(Player1.contains(44) && Player1.contains(53) && Player1.contains(62)&& Player1.contains(71)&& Player1.contains(80)){ winner = 2; }
/********* Dọc 8 *********/
        if(Player1.contains(9) && Player1.contains(18) && Player1.contains(27)&& Player1.contains(36)&& Player1.contains(45)){ winner = 2; }
        if(Player1.contains(18) && Player1.contains(27) && Player1.contains(36)&& Player1.contains(45)&& Player1.contains(54)){ winner = 2; }
        if(Player1.contains(27) && Player1.contains(36) && Player1.contains(45)&& Player1.contains(54)&& Player1.contains(63)){ winner = 2; }
        if(Player1.contains(26) && Player1.contains(45) && Player1.contains(54)&& Player1.contains(63)&& Player1.contains(72)){ winner = 2; }
        if(Player1.contains(45) && Player1.contains(54) && Player1.contains(63)&& Player1.contains(72)&& Player1.contains(81)){ winner = 2; }

/********* cheo 1 *********/
        if(Player1.contains(37) && Player1.contains(47) && Player1.contains(57)&& Player1.contains(67)&& Player1.contains(77)){ winner = 2; }
        if(Player1.contains(28) && Player1.contains(38) && Player1.contains(48)&& Player1.contains(58)&& Player1.contains(68)){ winner = 2; }
        if(Player1.contains(38) && Player1.contains(48) && Player1.contains(58)&& Player1.contains(68)&& Player1.contains(78)){ winner = 2; }
        if(Player1.contains(19) && Player1.contains(29) && Player1.contains(39)&& Player1.contains(49)&& Player1.contains(59)){ winner = 2; }
        if(Player1.contains(29) && Player1.contains(39) && Player1.contains(49)&& Player1.contains(59)&& Player1.contains(69)){ winner = 2; }
        if(Player1.contains(39) && Player1.contains(49) && Player1.contains(59)&& Player1.contains(69)&& Player1.contains(79)){ winner = 2; }
        if(Player1.contains(10) && Player1.contains(20) && Player1.contains(30)&& Player1.contains(40)&& Player1.contains(50)){ winner = 2; }
        if(Player1.contains(20) && Player1.contains(30) && Player1.contains(40)&& Player1.contains(50)&& Player1.contains(60)){ winner = 2; }
        if(Player1.contains(30) && Player1.contains(40) && Player1.contains(60)&& Player1.contains(70)&& Player1.contains(80)){ winner = 2; }
        if(Player1.contains(1) && Player1.contains(11) && Player1.contains(21)&& Player1.contains(31)&& Player1.contains(41)){ winner = 2; }
        if(Player1.contains(11) && Player1.contains(21) && Player1.contains(31)&& Player1.contains(41)&& Player1.contains(51)){ winner = 2; }
        if(Player1.contains(21) && Player1.contains(31) && Player1.contains(41)&& Player1.contains(51)&& Player1.contains(61)){ winner = 2; }
        if(Player1.contains(31) && Player1.contains(41) && Player1.contains(51)&& Player1.contains(61)&& Player1.contains(71)){ winner = 2; }
        if(Player1.contains(41) && Player1.contains(51) && Player1.contains(61)&& Player1.contains(71)&& Player1.contains(81)){ winner = 2; }
        if(Player1.contains(2) && Player1.contains(12) && Player1.contains(22)&& Player1.contains(32)&& Player1.contains(42)){ winner = 2; }
        if(Player1.contains(12) && Player1.contains(22) && Player1.contains(32)&& Player1.contains(42)&& Player1.contains(52)){ winner = 2; }
        if(Player1.contains(22) && Player1.contains(32) && Player1.contains(42)&& Player1.contains(52)&& Player1.contains(62)){ winner = 2; }
        if(Player1.contains(32) && Player1.contains(42) && Player1.contains(52)&& Player1.contains(62)&& Player1.contains(72)){ winner = 2; }
        if(Player1.contains(3) && Player1.contains(13) && Player1.contains(23)&& Player1.contains(33)&& Player1.contains(43)){ winner = 2; }
        if(Player1.contains(13) && Player1.contains(23) && Player1.contains(33)&& Player1.contains(43)&& Player1.contains(53)){ winner = 2; }
        if(Player1.contains(23) && Player1.contains(33) && Player1.contains(43)&& Player1.contains(53)&& Player1.contains(63)){ winner = 2; }
        if(Player1.contains(4) && Player1.contains(14) && Player1.contains(24)&& Player1.contains(34)&& Player1.contains(44)){ winner = 2; }
        if(Player1.contains(14) && Player1.contains(24) && Player1.contains(34)&& Player1.contains(44)&& Player1.contains(54)){ winner = 2; }
        if(Player1.contains(5) && Player1.contains(15) && Player1.contains(25)&& Player1.contains(35)&& Player1.contains(45)){ winner = 2; }
        /********* cheo 2 *********/
        if(Player1.contains(5) && Player1.contains(13) && Player1.contains(21)&& Player1.contains(29)&& Player1.contains(37)){ winner = 2; }
        if(Player1.contains(6) && Player1.contains(14) && Player1.contains(22)&& Player1.contains(30)&& Player1.contains(38)){ winner = 2; }
        if(Player1.contains(14) && Player1.contains(22) && Player1.contains(30)&& Player1.contains(38)&& Player1.contains(46)){ winner = 2; }
        if(Player1.contains(7) && Player1.contains(15) && Player1.contains(23)&& Player1.contains(31)&& Player1.contains(39)){ winner = 2; }
        if(Player1.contains(15) && Player1.contains(23) && Player1.contains(31)&& Player1.contains(39)&& Player1.contains(47)){ winner = 2; }
        if(Player1.contains(23) && Player1.contains(31) && Player1.contains(39)&& Player1.contains(47)&& Player1.contains(55)){ winner = 2; }

        if(Player1.contains(8) && Player1.contains(16) && Player1.contains(24)&& Player1.contains(32)&& Player1.contains(40)){ winner = 2; }
        if(Player1.contains(16) && Player1.contains(24) && Player1.contains(32)&& Player1.contains(40)&& Player1.contains(48)){ winner = 2; }
        if(Player1.contains(24) && Player1.contains(32) && Player1.contains(40)&& Player1.contains(48)&& Player1.contains(56)){ winner = 2; }
        if(Player1.contains(32) && Player1.contains(40) && Player1.contains(48)&& Player1.contains(56)&& Player1.contains(64)){ winner = 2; }

        if(Player1.contains(9) && Player1.contains(17) && Player1.contains(25)&& Player1.contains(33)&& Player1.contains(41)){ winner = 2; }
        if(Player1.contains(17) && Player1.contains(25) && Player1.contains(33)&& Player1.contains(41)&& Player1.contains(49)){ winner = 2; }
        if(Player1.contains(25) && Player1.contains(33) && Player1.contains(41)&& Player1.contains(49)&& Player1.contains(57)){ winner = 2; }
        if(Player1.contains(33) && Player1.contains(41) && Player1.contains(49)&& Player1.contains(57)&& Player1.contains(65)){ winner = 2; }
        if(Player1.contains(41) && Player1.contains(49) && Player1.contains(57)&& Player1.contains(65)&& Player1.contains(73)){ winner = 2; }

        if(Player1.contains(18) && Player1.contains(26) && Player1.contains(34)&& Player1.contains(42)&& Player1.contains(50)){ winner = 2; }
        if(Player1.contains(26) && Player1.contains(34) && Player1.contains(42)&& Player1.contains(50)&& Player1.contains(58)){ winner = 2; }
        if(Player1.contains(34) && Player1.contains(42) && Player1.contains(50)&& Player1.contains(58)&& Player1.contains(66)){ winner = 2; }
        if(Player1.contains(42) && Player1.contains(50) && Player1.contains(58)&& Player1.contains(66)&& Player1.contains(74)){ winner = 2; }

        if(Player1.contains(27) && Player1.contains(35) && Player1.contains(43)&& Player1.contains(51)&& Player1.contains(59)){ winner = 2; }
        if(Player1.contains(35) && Player1.contains(43) && Player1.contains(51)&& Player1.contains(59)&& Player1.contains(67)){ winner = 2; }
        if(Player1.contains(43) && Player1.contains(51) && Player1.contains(59)&& Player1.contains(67)&& Player1.contains(75)){ winner = 2; }

        if(Player1.contains(36) && Player1.contains(44) && Player1.contains(52)&& Player1.contains(60)&& Player1.contains(61)){ winner = 2; }
        if(Player1.contains(44) && Player1.contains(52) && Player1.contains(60)&& Player1.contains(68)&& Player1.contains(76)){ winner = 2; }

        if(Player1.contains(45) && Player1.contains(53) && Player1.contains(61)&& Player1.contains(69)&& Player1.contains(77)){ winner = 2; }

        if(winner != 0 && gameState == 1){
            if(winner == 1){
                ShowAlert(otherPlayer +" Thắng");
            }else if(winner == 2){
                ShowAlert("Bạn đã Thắng");
            }
            gameState = 2;
        }

        ArrayList<Integer> emptyBlocks = new ArrayList<Integer>();
        for(int i=1; i<=81; i++){
            if(!(Player1.contains(i) || Player2.contains(i))){
                emptyBlocks.add(i);
            }
        }
        if(emptyBlocks.size() == 0) {
            if(gameState == 1) {
                AlertDialog.Builder b = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                ShowAlert("Hòa");
            }
            gameState = 3;
        }
    }
    void ShowAlert(String Title) {

        AlertDialog.Builder b = new AlertDialog.Builder(this, R.style.TransparentDialog);

        b.setTitle(Title)

                .setMessage("Nhấn Nút Menu để bắt đầu lại ?")

                .setNegativeButton("Menu", new DialogInterface.OnClickListener( ) {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getApplicationContext( ), MenuActivity.class);

                        startActivity(intent);

                        finish( );
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)

                .show( );

    }



    void setEnableClick(boolean trueORfalse){
        ImageView iv;
        iv = (ImageView) findViewById(R.id.iv11); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv12); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv13); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv14); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv15); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv16); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv17); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv18); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv19); iv.setClickable(trueORfalse);

        iv = (ImageView) findViewById(R.id.iv21); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv22); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv23); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv24); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv25); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv26); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv27); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv28); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv29); iv.setClickable(trueORfalse);

        iv = (ImageView) findViewById(R.id.iv31); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv32); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv33); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv34); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv35); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv36); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv37); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv38); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv39); iv.setClickable(trueORfalse);

        iv = (ImageView) findViewById(R.id.iv41); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv42); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv43); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv44); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv45); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv46); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv47); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv48); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv49); iv.setClickable(trueORfalse);

        iv = (ImageView) findViewById(R.id.iv51); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv52); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv53); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv54); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv55); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv56); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv57); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv58); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv59); iv.setClickable(trueORfalse);

        iv = (ImageView) findViewById(R.id.iv61); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv62); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv63); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv64); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv65); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv66); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv67); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv68); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv69); iv.setClickable(trueORfalse);

        iv = (ImageView) findViewById(R.id.iv71); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv72); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv73); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv74); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv75); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv76); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv77); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv78); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv79); iv.setClickable(trueORfalse);

        iv = (ImageView) findViewById(R.id.iv81); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv82); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv83); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv84); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv85); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv86); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv87); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv88); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv89); iv.setClickable(trueORfalse);

        iv = (ImageView) findViewById(R.id.iv91); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv92); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv93); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv94); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv95); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv96); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv97); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv98); iv.setClickable(trueORfalse);
        iv = (ImageView) findViewById(R.id.iv99); iv.setClickable(trueORfalse);
    }

    void OtherPlayer(int selectedBlock) {
        ImageView selectedImage = (ImageView) findViewById(R.id.iv11);
        switch (selectedBlock) {
            case 1:
                selectedImage = (ImageView) findViewById(R.id.iv11);
                break;
            case 2:
                selectedImage = (ImageView) findViewById(R.id.iv12);
                break;
            case 3:
                selectedImage = (ImageView) findViewById(R.id.iv13);
                break;
            case 4:
                selectedImage = (ImageView) findViewById(R.id.iv14);
                break;
            case 5:
                selectedImage = (ImageView) findViewById(R.id.iv15);
                break;
            case 6:
                selectedImage = (ImageView) findViewById(R.id.iv16);
                break;
            case 7:
                selectedImage = (ImageView) findViewById(R.id.iv17);
                break;
            case 8:
                selectedImage = (ImageView) findViewById(R.id.iv18);
                break;
            case 9:
                selectedImage = (ImageView) findViewById(R.id.iv19);
                break;
            case 10:
                selectedImage = (ImageView) findViewById(R.id.iv21);
                break;
            case 11:
                selectedImage = (ImageView) findViewById(R.id.iv22);
                break;
            case 12:
                selectedImage = (ImageView) findViewById(R.id.iv23);
                break;
            case 13:
                selectedImage = (ImageView) findViewById(R.id.iv24);
                break;
            case 14:
                selectedImage = (ImageView) findViewById(R.id.iv25);
                break;
            case 15:
                selectedImage = (ImageView) findViewById(R.id.iv26);
                break;
            case 16:
                selectedImage = (ImageView) findViewById(R.id.iv27);
                break;
            case 17:
                selectedImage = (ImageView) findViewById(R.id.iv28);
                break;
            case 18:
                selectedImage = (ImageView) findViewById(R.id.iv29);
                break;
            case 19:
                selectedImage = (ImageView) findViewById(R.id.iv31);
                break;
            case 20:
                selectedImage = (ImageView) findViewById(R.id.iv32);
                break;
            case 21:
                selectedImage = (ImageView) findViewById(R.id.iv33);
                break;
            case 22:
                selectedImage = (ImageView) findViewById(R.id.iv34);
                break;
            case 23:
                selectedImage = (ImageView) findViewById(R.id.iv35);
                break;
            case 24:
                selectedImage = (ImageView) findViewById(R.id.iv36);
                break;
            case 25:
                selectedImage = (ImageView) findViewById(R.id.iv37);
                break;
            case 26:
                selectedImage = (ImageView) findViewById(R.id.iv38);
                break;
            case 27:
                selectedImage = (ImageView) findViewById(R.id.iv39);
                break;
            case 28:
                selectedImage = (ImageView) findViewById(R.id.iv41);
                break;
            case 29:
                selectedImage = (ImageView) findViewById(R.id.iv42);
                break;
            case 30:
                selectedImage = (ImageView) findViewById(R.id.iv43);
                break;
            case 31:
                selectedImage = (ImageView) findViewById(R.id.iv44);
                break;
            case 32:
                selectedImage = (ImageView) findViewById(R.id.iv45);
                break;
            case 33:
                selectedImage = (ImageView) findViewById(R.id.iv46);
                break;
            case 34:
                selectedImage = (ImageView) findViewById(R.id.iv47);
                break;
            case 35:
                selectedImage = (ImageView) findViewById(R.id.iv48);
                break;
            case 36:
                selectedImage = (ImageView) findViewById(R.id.iv49);
                break;
            case 37:
                selectedImage = (ImageView) findViewById(R.id.iv51);
                break;
            case 38:
                selectedImage = (ImageView) findViewById(R.id.iv52);
                break;
            case 39:
                selectedImage = (ImageView) findViewById(R.id.iv53);
                break;
            case 40:
                selectedImage = (ImageView) findViewById(R.id.iv54);
                break;
            case 41:
                selectedImage = (ImageView) findViewById(R.id.iv55);
                break;
            case 42:
                selectedImage = (ImageView) findViewById(R.id.iv56);
                break;
            case 43:
                selectedImage = (ImageView) findViewById(R.id.iv57);
                break;
            case 44:
                selectedImage = (ImageView) findViewById(R.id.iv58);
                break;
            case 45:
                selectedImage = (ImageView) findViewById(R.id.iv59);
                break;
            case 46:
                selectedImage = (ImageView) findViewById(R.id.iv61);
                break;
            case 47:
                selectedImage = (ImageView) findViewById(R.id.iv62);
                break;
            case 48:
                selectedImage = (ImageView) findViewById(R.id.iv63);
                break;
            case 49:
                selectedImage = (ImageView) findViewById(R.id.iv64);
                break;
            case 50:
                selectedImage = (ImageView) findViewById(R.id.iv65);
                break;
            case 51:
                selectedImage = (ImageView) findViewById(R.id.iv66);
                break;
            case 52:
                selectedImage = (ImageView) findViewById(R.id.iv67);
                break;
            case 53:
                selectedImage = (ImageView) findViewById(R.id.iv68);
                break;
            case 54:
                selectedImage = (ImageView) findViewById(R.id.iv69);
                break;
            case 55:
                selectedImage = (ImageView) findViewById(R.id.iv71);
                break;
            case 56:
                selectedImage = (ImageView) findViewById(R.id.iv72);
                break;
            case 57:
                selectedImage = (ImageView) findViewById(R.id.iv73);
                break;
            case 58:
                selectedImage = (ImageView) findViewById(R.id.iv74);
                break;
            case 59:
                selectedImage = (ImageView) findViewById(R.id.iv75);
                break;
            case 60:
                selectedImage = (ImageView) findViewById(R.id.iv76);
                break;
            case 61:
                selectedImage = (ImageView) findViewById(R.id.iv77);
                break;
            case 62:
                selectedImage = (ImageView) findViewById(R.id.iv78);
                break;
            case 63:
                selectedImage = (ImageView) findViewById(R.id.iv79);
                break;
            case 64:
                selectedImage = (ImageView) findViewById(R.id.iv81);
                break;
            case 65:
                selectedImage = (ImageView) findViewById(R.id.iv82);
                break;
            case 66:
                selectedImage = (ImageView) findViewById(R.id.iv83);
                break;
            case 67:
                selectedImage = (ImageView) findViewById(R.id.iv84);
                break;
            case 68:
                selectedImage = (ImageView) findViewById(R.id.iv85);
                break;
            case 69:
                selectedImage = (ImageView) findViewById(R.id.iv86);
                break;
            case 70:
                selectedImage = (ImageView) findViewById(R.id.iv87);
                break;
            case 71:
                selectedImage = (ImageView) findViewById(R.id.iv88);
                break;
            case 72:
                selectedImage = (ImageView) findViewById(R.id.iv89);
                break;
            case 73:
                selectedImage = (ImageView) findViewById(R.id.iv91);
                break;
            case 74:
                selectedImage = (ImageView) findViewById(R.id.iv92);
                break;
            case 75:
                selectedImage = (ImageView) findViewById(R.id.iv93);
                break;
            case 76:
                selectedImage = (ImageView) findViewById(R.id.iv94);
                break;
            case 77:
                selectedImage = (ImageView) findViewById(R.id.iv95);
                break;
            case 78:
                selectedImage = (ImageView) findViewById(R.id.iv96);
                break;
            case 79:
                selectedImage = (ImageView) findViewById(R.id.iv97);
                break;
            case 80:
                selectedImage = (ImageView) findViewById(R.id.iv98);
                break;
            case 81:
                selectedImage = (ImageView) findViewById(R.id.iv99);
                break;
        }

        PlayGame(selectedBlock, selectedImage);

    }

    void ResetGame(){
        gameState = 1;
        activePlayer = 1;
        Player1.clear();
        Player2.clear();
        myRef.child("playing").child(playerSession).removeValue();
        ImageView iv;

        iv = (ImageView) findViewById(R.id.iv11); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv12); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv13); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv14); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv15); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv16); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv17); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv18); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv19); iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv21); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv22); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv23); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv24); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv25); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv26); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv27); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv28); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv29);iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv31); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv32); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv33); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv34); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv35); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv36); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv37); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv38); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv39); iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv41); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv42); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv43); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv44); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv45); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv46); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv47); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv48); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv49); iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv51); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv52); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv53); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv54); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv55); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv56); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv57); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv58); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv59); iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv61); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv62); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv63); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv64); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv65); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv66); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv67); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv68); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv69);iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv71); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv72); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv73); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv74); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv75); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv76); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv77); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv78); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv79); iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv81); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv82); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv83); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv84); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv85); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv86); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv87); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv88); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv89); iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv91); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv92); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv93); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv94); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv95); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv96); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv97); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv98); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv99); iv.setImageResource(0); iv.setEnabled(true);



    }

    public void GameBroadCick(View view) {
        ImageView selectedImage = (ImageView) view;
        if(playerSession.length() <= 0){
            Intent intent = new Intent(getApplicationContext(), OnlineLoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            int selectedBlock = 0;
            amthanh.play(id_amthanh,1.0f,1.0f,1,0,1.0f);
            switch ((selectedImage.getId( ))) {
                case R.id.iv11:
                    selectedBlock = 1;
                    break;
                case R.id.iv12:
                    selectedBlock = 2;
                    break;
                case R.id.iv13:
                    selectedBlock = 3;
                    break;
                case R.id.iv14:
                    selectedBlock = 4;
                    break;
                case R.id.iv15:
                    selectedBlock = 5;
                    break;
                case R.id.iv16:
                    selectedBlock = 6;
                    break;
                case R.id.iv17:
                    selectedBlock = 7;
                    break;
                case R.id.iv18:
                    selectedBlock = 8;
                    break;
                case R.id.iv19:
                    selectedBlock = 9;
                    break;

                case R.id.iv21:
                    selectedBlock = 10;
                    break;
                case R.id.iv22:
                    selectedBlock = 11;
                    break;
                case R.id.iv23:
                    selectedBlock = 12;
                    break;
                case R.id.iv24:
                    selectedBlock = 13;
                    break;
                case R.id.iv25:
                    selectedBlock = 14;
                    break;
                case R.id.iv26:
                    selectedBlock = 15;
                    break;
                case R.id.iv27:
                    selectedBlock = 16;
                    break;
                case R.id.iv28:
                    selectedBlock = 17;
                    break;
                case R.id.iv29:
                    selectedBlock = 18;
                    break;

                case R.id.iv31:
                    selectedBlock = 19;
                    break;
                case R.id.iv32:
                    selectedBlock = 20;
                    break;
                case R.id.iv33:
                    selectedBlock = 21;
                    break;
                case R.id.iv34:
                    selectedBlock = 22;
                    break;
                case R.id.iv35:
                    selectedBlock = 23;
                    break;
                case R.id.iv36:
                    selectedBlock = 24;
                    break;
                case R.id.iv37:
                    selectedBlock = 25;
                    break;
                case R.id.iv38:
                    selectedBlock = 26;
                    break;
                case R.id.iv39:
                    selectedBlock = 27;
                    break;

                case R.id.iv41:
                    selectedBlock = 28;
                    break;
                case R.id.iv42:
                    selectedBlock = 29;
                    break;
                case R.id.iv43:
                    selectedBlock = 30;
                    break;
                case R.id.iv44:
                    selectedBlock = 31;
                    break;
                case R.id.iv45:
                    selectedBlock = 32;
                    break;
                case R.id.iv46:
                    selectedBlock = 33;
                    break;
                case R.id.iv47:
                    selectedBlock = 34;
                    break;
                case R.id.iv48:
                    selectedBlock = 35;
                    break;
                case R.id.iv49:
                    selectedBlock = 36;
                    break;

                case R.id.iv51:
                    selectedBlock = 37;
                    break;
                case R.id.iv52:
                    selectedBlock = 38;
                    break;
                case R.id.iv53:
                    selectedBlock = 39;
                    break;
                case R.id.iv54:
                    selectedBlock = 40;
                    break;
                case R.id.iv55:
                    selectedBlock = 41;
                    break;
                case R.id.iv56:
                    selectedBlock = 42;
                    break;
                case R.id.iv57:
                    selectedBlock = 43;
                    break;
                case R.id.iv58:
                    selectedBlock = 44;
                    break;
                case R.id.iv59:
                    selectedBlock = 45;
                    break;

                case R.id.iv61:
                    selectedBlock = 46;
                    break;
                case R.id.iv62:
                    selectedBlock = 47;
                    break;
                case R.id.iv63:
                    selectedBlock = 48;
                    break;
                case R.id.iv64:
                    selectedBlock = 49;
                    break;
                case R.id.iv65:
                    selectedBlock = 50;
                    break;
                case R.id.iv66:
                    selectedBlock = 51;
                    break;
                case R.id.iv67:
                    selectedBlock = 52;
                    break;
                case R.id.iv68:
                    selectedBlock = 53;
                    break;
                case R.id.iv69:
                    selectedBlock = 54;
                    break;

                case R.id.iv71:
                    selectedBlock = 55;
                    break;
                case R.id.iv72:
                    selectedBlock = 56;
                    break;
                case R.id.iv73:
                    selectedBlock = 57;
                    break;
                case R.id.iv74:
                    selectedBlock = 58;
                    break;
                case R.id.iv75:
                    selectedBlock = 59;
                    break;
                case R.id.iv76:
                    selectedBlock = 60;
                    break;
                case R.id.iv77:
                    selectedBlock = 61;
                    break;
                case R.id.iv78:
                    selectedBlock = 62;
                    break;
                case R.id.iv79:
                    selectedBlock = 63;
                    break;

                case R.id.iv81:
                    selectedBlock = 64;
                    break;
                case R.id.iv82:
                    selectedBlock = 65;
                    break;
                case R.id.iv83:
                    selectedBlock = 66;
                    break;
                case R.id.iv84:
                    selectedBlock = 67;
                    break;
                case R.id.iv85:
                    selectedBlock = 68;
                    break;
                case R.id.iv86:
                    selectedBlock = 69;
                    break;
                case R.id.iv87:
                    selectedBlock = 70;
                    break;
                case R.id.iv88:
                    selectedBlock = 71;
                    break;
                case R.id.iv89:
                    selectedBlock = 72;
                    break;

                case R.id.iv91:
                    selectedBlock = 73;
                    break;
                case R.id.iv92:
                    selectedBlock = 74;
                    break;
                case R.id.iv93:
                    selectedBlock = 75;
                    break;
                case R.id.iv94:
                    selectedBlock = 76;
                    break;
                case R.id.iv95:
                    selectedBlock = 77;
                    break;
                case R.id.iv96:
                    selectedBlock = 78;
                    break;
                case R.id.iv97:
                    selectedBlock = 79;
                    break;
                case R.id.iv98:
                    selectedBlock = 80;
                    break;
                case R.id.iv99:
                    selectedBlock = 81;
                    break;
            }
            myRef.child("playing").child(playerSession).child("game").child("block:" + selectedBlock).setValue(userName);
            myRef.child("playing").child(playerSession).child("turn").setValue(otherPlayer);
            setEnableClick(false);
            activePlayer = 2;
            PlayGame(selectedBlock, selectedImage);
        }
    }
}
