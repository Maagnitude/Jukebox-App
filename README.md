# Jukebox-App

This is a simple terminal based Jukebox implementation, for our first assignment in Object-Oriented Programming using Java ( 3rd semester at Harokopio University )

(Still on progress...)


It only works with mp3 and m3u files, and supports the following functionalities:

## First we change to the jar directory
```bash
cd <path-to-project-folder>/out/artifacts/JukeBox_jar
```


## 1. Playing a song to its end
```bash
java -jar JukeBox.jar testsong.mp3 
```
The command, above, works only if the song is in the JukeBox_jar obviously. Additionally the user can give the path to the song folder along with the song name and it will still work, as following:

```bash
java -jar JukeBox.jar <path-to-song-folder>/testsong.mp3 
```

It can be stopped only by using Ctrl+C or Ctrl+Z.

## 2. Playing a playlist of mp3, created from a folder given
```bash
java -jar JukeBox.jar <path-to-playlist-folder>
```
or
```bash
java -jar JukeBox.jar <path-to-playlist-folder> order
```

The user gives a path to a folder and a playlist is being made containing only mp3 files. Then the playlist is being played, and the program stops when the playlist is finished. (every song in it, has been played once)

## 3. Playing a song in loop (Repeat one)
```bash
java -jar JukeBox.jar testsong.mp3 loop
```
or
```bash
java -jar JukeBox.jar <path-to-song-folder>/testsong.mp3 loop
```

It repeatedly plays a song, and stops only with the use of Ctrl+C or Ctrl+Z

## 4. Playing a playlist created from an m3u file
```bash
java -jar JukeBox.jar <path-to-m3u-file>/playlist.m3u
```
or
```bash
java -jar JukeBox.jar <path-to-m3u-file>/playlist.m3u order
```

It reads the m3u file, finds the songs (mp3 files) in it and their paths, creates a playlist of them, and plays them in order. It stops when every one of them are played.(once)

## 5. Playing a shuffled playlist created from an m3u file
```bash
java -jar JukeBox.jar <path-to-m3u-file>/playlist.m3u random
```
It reads the m3u file, finds the songs (mp3 files) in it and their paths, creates a playlist of them, shuffles it and plays them in random. It stops when every one of them are played. (once)

There will also be a windowed application. Stay tuned!

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.
