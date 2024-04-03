package com.example.imagepro;

import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;
import android.widget.RelativeLayout;

public class SignKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;

    private KeyboardView keyboardView1;
    private Keyboard keyboard;
    private boolean isCap = false;

    private static final int KEYCODE_CAPITALIZE = -2;
    private static final int DOUBLE_TAP_TIME_THRESHOLD = 500; // Adjust this threshold as needed

    private long lastShiftPressTime = 0;

    private Keyboard alphabeticKeyboard;
    private Keyboard numbericKeyboard;

    private Keyboard t;
    private Keyboard symbolicKeyboard;
    private boolean isAlphabetic = true;
    private boolean isNumberic = true;

    private  String val = "";

    @Override
    public View onCreateInputView() {


        View rootView = getLayoutInflater().inflate(R.layout.keyboard_layout, null);
        View root = getLayoutInflater().inflate(R.layout.activity_voice_sign,null);

        keyboardView = rootView.findViewById(R.id.keyboard);



        /*EditText searchEditText = rootView.findViewById(R.id.searchEditText);
        Button processButton = rootView.findViewById(R.id.processButton);
        // Find and reference the EditText and "Process" button*/


        // Set listeners for the EditText and "Process" button
        /*searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            Log.d("SearchEditText", "EditorActionListener triggered");
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Handle search action here
                String searchText = searchEditText.getText().toString();
                Log.d("SearchEditText", "EditorActionListener triggered");
                // Perform video search and display results
                // You may want to implement this logic based on your requirements
                return true;
            }
            return false;
        });

        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                *//*Intent intent = new Intent(SignKeyboard.this, Voice_Sign.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*//*
                *//*Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.hurt/.Voice_Sign");
                if(launchIntent != null){
                    startActivity(launchIntent);
                }
                else{
                    Toast.makeText(SignKeyboard.this,"This is no package",Toast.LENGTH_LONG).show();
                }*//*

                Intent intent = new Intent();
                intent.setAction("com.example.hurt.ACTION_START_VOICE_SIGN");
                startActivity(intent);


            }
        });*/

        // Set the layout parameters for the KeyboardView
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, R.id.keyboard);
        keyboardView.setLayoutParams(params);

        keyboard = new Keyboard(this, R.xml.qwerty);
        numbericKeyboard = new Keyboard(this, R.xml.numeric_keyboard);
        symbolicKeyboard = new Keyboard(this, R.xml.symbol);
        t = new Keyboard(this,R.xml.test);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(this);

        return rootView;
    }

    /*private void sendVideo() {
        // Code to send the video
        // Use Intents to launch external apps for video sharing
        // Example: Share via WhatsApp
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.a_sign);
        *//*Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_STREAM, videoUri);
        intent.setPackage("com.whatsapp");  // Specify the package name for the target app
        startActivity(intent);*//*

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_STREAM, videoUri);
        startActivity(intent);
    }*/


    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {
        Log.d("KeyboardService", "Keycode: " + i);

        InputConnection inputConnection = getCurrentInputConnection();

        playClick(i);
        switch (i) {
            case 1001:
                // Open the settings activity
                Intent settingsIntent = new Intent(SignKeyboard.this, Voice_Sign.class);
                settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this line to add FLAG_ACTIVITY_NEW_TASK flag
                startActivity(settingsIntent);
                break;


            case Keyboard.KEYCODE_DELETE:
                inputConnection.deleteSurroundingText(1, 0);
                break;

            case Keyboard.KEYCODE_SHIFT:
                isCap = !isCap;
                keyboard.setShifted(isCap);
                Key shiftKey = findShiftKey(keyboard);
                if (shiftKey != null) {
                    // Change the icon based on the shift state
                    int newIconResId = isCap ? R.drawable.shift_plus : R.drawable.shift;
                    shiftKey.icon = getResources().getDrawable(newIconResId);
                }

                keyboardView.invalidateAllKeys();
                break;

            case Keyboard.KEYCODE_DONE:
                inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case -123:
                isAlphabetic = !isAlphabetic;
                keyboardView.setKeyboard(isAlphabetic ? keyboard : numbericKeyboard);
                break;

            case -58:
                isNumberic = !isNumberic;
                keyboardView.setKeyboard(isNumberic ? numbericKeyboard : symbolicKeyboard);
                break;

            default:
                char code = (char) i;
                if (Character.isLetter(code) && isCap) {
                    code = Character.toUpperCase(code);
                }
                if (!isAlphabetic) {
                    // Handle numeric/special character input
                    inputConnection.commitText(String.valueOf(code), 1);
                    val = val + code;
                }
                // If it's the alphabetic keyboard, commit the text as usual
                else {
                    inputConnection.commitText(String.valueOf(code), 1);

                    val = val + code;
                }
                break;
        }
    }


    private Key findShiftKey(Keyboard keyboard) {
        for (Key key : keyboard.getKeys()) {
            if (key.codes != null && key.codes.length > 0 && key.codes[0] == Keyboard.KEYCODE_SHIFT) {
                return key;
            }
        }
        return null;
    }

    private void playClick(int i) {
        AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        final long VIBRATION_DURATION = 20;

        switch (i){

            case 32:audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                vibrator.vibrate(VIBRATION_DURATION);
            break;

            case Keyboard.KEYCODE_DONE: case 10:audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                vibrator.vibrate(VIBRATION_DURATION);
            break;

            case Keyboard.KEYCODE_DELETE:
                InputConnection inputConnection = getCurrentInputConnection();
                CharSequence beforeText = inputConnection.getTextBeforeCursor(1, 0);
                if (!TextUtils.isEmpty(beforeText)) {
                    // Delete only if there is text
                    /*inputConnection.deleteSurroundingText(1, 0);
                    playClick(i);*/
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                    vibrator.vibrate(VIBRATION_DURATION);
                }

            break;
            default:audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                vibrator.vibrate(VIBRATION_DURATION);

        }

    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
