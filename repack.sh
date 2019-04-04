rm temp0.wav
ffmpeg -i temp.wav -acodec pcm_s16le temp0.wav
mv temp0.wav temp.wav