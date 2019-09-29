package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
                sneeze_player.start();
                health--;
                if (health < 0)
                {
                    health = 0;
                }
                Log.d("HEALTH", String.valueOf(health));
                checkHealth();
            }
        });

        nose_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                nose_player.start();
                Log.d("HEALTH", String.valueOf(health));
                checkHealth();
            }
        });

        medication_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                medication_player.start();
                health = health + 2;
                if (health > 10)
                {
                    health = 10;
                }
                Log.d("HEALTH", String.valueOf(health));
                checkHealth();
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
            sneeze_player.start();
            health--;
            if (health < 0)
            {
                health = 0;
            }
            Log.d("HEALTH", String.valueOf(health));
            checkHealth();
            counter++;
        }
        else if (counter == 1)
        {
            nose_player.start();
            Log.d("HEALTH", String.valueOf(health));
            checkHealth();
            counter++;
        }
        else if (counter == 2)
        {
            medication_player.start();
            health = health + 2;
            if (health > 10)
            {
                health = 10;
            }
            Log.d("HEALTH", String.valueOf(health));
            checkHealth();
            counter = 0;
        }

    }

    public void setActivityBackground(int color)
    {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

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
}
