package com.am.testerpointone;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPscreen extends AppCompatActivity {
    private static final String TAG = "123abc";
    Pinview pin;
    Button homebtn;
    Button resendbtn;
    FirebaseAuth mAuth;
    String number;
    String verificationId;
    TextView txt_timer;
    TextView senttomobnumber;
    private PrefManager prefManager;
    private boolean isCanceled = false;
    PhoneAuthProvider.ForceResendingToken resendforcetoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);
        pin = findViewById(R.id.pinview);
        homebtn = findViewById(R.id.button);
        resendbtn = findViewById(R.id.btnresend);
        number = getIntent().getStringExtra("phonenumber");
        txt_timer=(TextView)findViewById( R.id.textView6);
        senttomobnumber=(TextView)findViewById( R.id.textView5);
        senttomobnumber.setText(number);


        mAuth = FirebaseAuth.getInstance();

        prefManager = new PrefManager(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Enter OTP");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
 new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if(isCanceled)
                {
                    //If the user request to cancel or paused the
                    //CountDownTimer we will cancel the current instance
                    cancel();
                }else {
                    long millis = millisUntilFinished;
                    //Convert milliseconds into hour,minute and seconds
                    String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                    txt_timer.setText(hms);//set text
                }
            }
            public void onFinish() {
                resendbtn.setVisibility(View.VISIBLE);
            }
        }.start();

        pin.setTextColor(getResources().getColor(R.color.txt_col));

        sendVerificationCode(number);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = pin.getValue().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    Toast.makeText(OTPscreen.this, "invaild otp", Toast.LENGTH_SHORT).show();
                    return;
                }
                verifyCode(code);
            }
        });

        resendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                resendVerificationCode(number,resendforcetoken);
            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(OTPscreen.this, MainActivity2.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Toast.makeText(OTPscreen.this, "success!", Toast.LENGTH_SHORT).show();
                            prefManager.setMyMobNum(number);
                            prefManager.setotpdone(true);
                            startActivity(intent);
                            finish();

                        } else {
                            //  Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Toast.makeText(OTPscreen.this, "Error please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallBack,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    private void sendVerificationCode(String number) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            resendforcetoken = forceResendingToken;
            Log.i("fvsddfbnsghdf","code sent");

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            Log.i("fvsddfbnsghdf12e",code);

            if (code != null) {
                Log.i("fvsddfbnsghdfasf",code);

                pin.setValue(code);
                verifyCode(code);
                Log.i("fvsddfbnsghdf",code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTPscreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("fvsddfbnsghdf","code failed"+e.getMessage());
        }
    };

}

//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback
//            = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//            String code = phoneAuthCredential.getSmsCode();
//            Toast.makeText(OTPscreen.this, ""+code, Toast.LENGTH_SHORT).show();
//
//            if(code!=null){
//                Log.i("123ac this is code",code);
//                verifyVerficationCode(code);
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(@NonNull FirebaseException e) {
//
//            Log.i("sdfaaa",e.getMessage());
//            Toast.makeText(OTPscreen.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            mVerificationId=s;
//        }
//    };
//
//    private void verifyVerficationCode(String code) {
//
//        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(mVerificationId,code);
//        signInWithPhoneAuthCredential(credential);
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(OTPscreen.this,
//                        new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()){
//                                    Intent intent = new Intent(OTPscreen.this,
//                                            Home.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|
//                                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
//
//                                }else {
//                                    Toast.makeText(OTPscreen.this, "invaild code", Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        });
//
//    }}