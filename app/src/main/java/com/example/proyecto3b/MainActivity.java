package com.example.proyecto3b;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextureView textureViewTop;
    private TextureView textureViewLeft;
    private TextureView textureViewRight;
    private TextureView textureView180;
    private Button playButton;

    private MediaPlayer mediaPlayerTop;
    private MediaPlayer mediaPlayerLeft;
    private MediaPlayer mediaPlayerRight;
    private MediaPlayer mediaPlayer180;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        // Inicializamos las vistas de video (TextureView en lugar de SurfaceView)
        textureViewTop = findViewById(R.id.videoView);
        textureViewLeft = findViewById(R.id.videoViewLeft);
        textureViewRight = findViewById(R.id.videoViewRight);
        textureView180 = findViewById(R.id.videoView180);
        playButton = findViewById(R.id.playButton);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.mivideo1;
        Uri uri = Uri.parse(videoPath);

        // Configuramos la rotación en las vistas de video
        textureViewLeft.setRotation(270);   // Rotación 90° a la izquierda
        textureViewRight.setRotation(90); // Rotación 90° a la derecha (270°)
        textureView180.setRotation(180);  // Rotación 180°

        // Configuramos los MediaPlayer y TextureView para cada video
        setupMediaPlayer(textureViewTop, uri, 0, false);    // Video sin rotación
        setupMediaPlayer(textureViewLeft, uri, 270, true);  // Video rotado 90° a la izquierda
        setupMediaPlayer(textureViewRight, uri, 90, true); // Video rotado 90° a la derecha
        setupMediaPlayer(textureView180, uri, 180, true);  // Video rotado 180°

        // Configuramos el botón de reproducción
        playButton.setEnabled(false);  // Inicialmente deshabilitado
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrPauseAllVideos();
            }
        });
    }

    // Método para configurar MediaPlayer y asociar a TextureView
    private void setupMediaPlayer(TextureView textureView, Uri uri, int rotationAngle, boolean mute) {
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(getApplicationContext(), uri);
                    mediaPlayer.setSurface(new Surface(surface));  // Asociamos el Surface del TextureView

                    if (mute) {
                        mediaPlayer.setVolume(0f, 0f);  // Establecer volumen a 0 (silenciar)
                    } else {
                        mediaPlayer.setVolume(1f, 1f);  // Establecer volumen normal (1f, 1f)
                    }

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            playButton.setEnabled(true);
                            Toast.makeText(MainActivity.this, "Video listo para reproducir", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mediaPlayer.prepareAsync(); // Preparar el video asíncronamente

                    // Asociamos el MediaPlayer con su TextureView respectivo
                    if (rotationAngle == 0) {
                        mediaPlayerTop = mediaPlayer;
                    } else if (rotationAngle == 270) {
                        mediaPlayerLeft = mediaPlayer;
                    } else if (rotationAngle == 90) {
                        mediaPlayerRight = mediaPlayer;
                    } else if (rotationAngle == 180) {
                        mediaPlayer180 = mediaPlayer;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error al cargar el video: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                // Puedes manejar cambios de tamaño de la textura si es necesario
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                if (mediaPlayerTop != null) {
                    mediaPlayerTop.release();
                    mediaPlayerTop = null;
                }
                if (mediaPlayerLeft != null) {
                    mediaPlayerLeft.release();
                    mediaPlayerLeft = null;
                }
                if (mediaPlayerRight != null) {
                    mediaPlayerRight.release();
                    mediaPlayerRight = null;
                }
                if (mediaPlayer180 != null) {
                    mediaPlayer180.release();
                    mediaPlayer180 = null;
                }
                return true; // Indica que hemos manejado la destrucción
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                // Puedes manejar actualizaciones de la textura si es necesario
            }
        });
    }

    // Método para iniciar todos los videos
    private void startOrPauseAllVideos() {
        if (mediaPlayerTop != null && mediaPlayerTop.isPlaying()) {
            // Pausar el video si está en reproducción
            mediaPlayerTop.pause();
        } else if (mediaPlayerTop != null && !mediaPlayerTop.isPlaying()) {
            // Reproducir el video si está pausado
            mediaPlayerTop.start();
        }

        if (mediaPlayerLeft != null && mediaPlayerLeft.isPlaying()) {
            mediaPlayerLeft.pause();
        } else if (mediaPlayerLeft != null && !mediaPlayerLeft.isPlaying()) {
            mediaPlayerLeft.start();
        }

        if (mediaPlayerRight != null && mediaPlayerRight.isPlaying()) {
            mediaPlayerRight.pause();
        } else if (mediaPlayerRight != null && !mediaPlayerRight.isPlaying()) {
            mediaPlayerRight.start();
        }

        if (mediaPlayer180 != null && mediaPlayer180.isPlaying()) {
            mediaPlayer180.pause();
        } else if (mediaPlayer180 != null && !mediaPlayer180.isPlaying()) {
            mediaPlayer180.start();
        }

        // Cambiar el texto del botón según si los videos están en pausa o reproducción
        if (mediaPlayerTop != null && mediaPlayerTop.isPlaying()) {
            playButton.setText("Pausar");
        } else {
            playButton.setText("Reproducir");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberamos los recursos cuando la actividad se destruye
        if (mediaPlayerTop != null) {
            mediaPlayerTop.stop();
            mediaPlayerTop.release();
        }
        if (mediaPlayerLeft != null) {
            mediaPlayerLeft.stop();
            mediaPlayerLeft.release();
        }
        if (mediaPlayerRight != null) {
            mediaPlayerRight.stop();
            mediaPlayerRight.release();
        }
        if (mediaPlayer180 != null) {
            mediaPlayer180.stop();
            mediaPlayer180.release();
        }
    }
}