package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button sneeze_btn, nose_btn, medication_btn;
    private MediaPlayer sneeze_player, nose_player, medication_player;
    private int counter = 0;
    private int health = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sneeze_btn = findViewById(R.id.button_sneeze);
        nose_btn = findViewById(R.id.button_nose);
        medication_btn = findViewById(R.id.button_medication);

        sneeze_player = MediaPlayer.create(this, R.raw.sneeze2);
        nose_player = MediaPlayer.create(this, R.raw.blow_nose);
        medication_player = MediaPlayer.create(this, R.raw.slurp);

        sneeze_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // 1 means sneeze player
                playSound(sneeze_player, 1);
            }
        });

        nose_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // 2 means nose player
                playSound(nose_player, 2);
            }
        });

        medication_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // 3 means medication player
                playSound(medication_player, 3);
            }
        });
    }

    // When screen changes orientation
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        sneeze_player = MediaPlayer.create(this, R.raw.sneeze2);
        nose_player = MediaPlayer.create(this, R.raw.blow_nose);
        medication_player = MediaPlayer.create(this, R.raw.slurp);

        super.onConfigurationChanged(newConfig);
        if (counter == 0)
        {
            // 1 means sneeze player
            playSound(sneeze_player, 1);
        }
        else if (counter == 1)
        {
            // 2 means nose player
            playSound(nose_player, 2);
        }
    }

    // Method that plays appropriate sound and make some back end changes depending on which sound plays
    // playerType [1 = sneeze, 2 = blow nose, 3 = medication]
    public void playSound(MediaPlayer player, int playerType)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text;

        player.start();
        if (playerType == 1) {
            health--;
            if (health < 0) {
                health = 0;
            }
            counter = 1;
        }
        else if (playerType == 2)
        {
            counter = 0;
        }
        else if (playerType == 3)
        {
            health = health + 2;
            if (health > 10)
            {
                health = 10;
            }
        }
        text = "Health: " + health;
        checkHealth();
        Log.d("HEALTH", String.valueOf(health));
        Toast msg = Toast.makeText(context, text, duration);
        msg.show();
    }

    // Method that check health and changes color accordingly
    public void checkHealth()
    {
        if (health <= 5)
        {
            setActivityBackground(Color.RED);
        }
        else if (health <= 7)
        {
            setActivityBackground(Color.BLUE);
        }
        else
        {
            setActivityBackground(Color.WHITE);
        }
    }

    // Method that changes background color
    public void setActivityBackground(int color)
    {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
}
