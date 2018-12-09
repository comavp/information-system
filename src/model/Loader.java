package model;

import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Loader implements ModelLoader {

    private TreeMap<Genre, ArrayList<Track>> modelList;
    private String defaultPathToFile = "MusicLibrary.dat";
    private String pathToFile = defaultPathToFile;
    private String result;

    public String getResult() {
        return result;
    }

    @Override
    public void saveModel(TreeMap<Genre, ArrayList<Track>> modelList) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pathToFile));
        out.writeObject(modelList);
        out.close();
    }

    public TreeMap<Genre, ArrayList<Track>> getModel() throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(pathToFile));
            return  (TreeMap<Genre, ArrayList<Track>>)in.readObject();
        }
        catch (EOFException e) {
            return new TreeMap<Genre, ArrayList<Track>>();
        }
    }

    public void addModel(Genre genre, Track track) throws IOException, ClassNotFoundException {
        modelList = getModel();
        if (modelList.containsKey(genre)) {
            if(!modelList.get(genre).contains(track)) {
                modelList.get(genre).add(track);
                saveModel(modelList);
                result = track.getTitle() + "(" + track.getArtist() + ", " + track.getAlbum() +  ", " + track.getLength() + ")" + ".Добавлен";
            } else {
                result = "Такой трек уже существует!";
            }
        } else {
            modelList.put(genre, new ArrayList<Track>());
            modelList.get(genre).add(track);
            saveModel(modelList);
            result = genre.getName() + "." + track.getTitle() + "(" + track.getArtist() + ", " + track.getAlbum() +  ", " +
                    track.getLength() + ")" + ".Добавлен";
        }
    }

    public void changeModel(String newGenreName, Track newTrack, Genre genre, Integer indexOfTrack) throws IOException, ClassNotFoundException {
        try {
            modelList = getModel();
            if (modelList.containsKey(genre)) {
                Track oldTrack = modelList.get(genre).get(indexOfTrack);

                if (!newGenreName.equals("-")) {
                    modelList.floorKey(genre).setName(newGenreName);
                }
                if (!newTrack.getTitle().equals("-")) {
                    oldTrack.setTitle(newTrack.getTitle());
                }
                if (!newTrack.getArtist().equals("-")) {
                    oldTrack.setArtist(newTrack.getArtist());
                }
                if (!newTrack.getAlbum().equals("-")) {
                    oldTrack.setAlbum(newTrack.getAlbum());
                }
                if (newTrack.getLength() != 0) {
                    oldTrack.setLength(newTrack.getLength());
                }
                result = modelList.floorKey(genre).getName() + "." + oldTrack.getTitle() + "(" + oldTrack.getArtist() + ", " + oldTrack.getAlbum() + ", "
                        + oldTrack.getLength() + ")" + ".Изменён";
                saveModel(modelList);
            } else {
                result = "Нет жанра с таким названием!";
            }
        }
        catch (IndexOutOfBoundsException e) {
            result = "Такого трека не существует!";
        }
    }

    public void deleteModel(Genre genre, Track track) throws IOException, ClassNotFoundException {
        modelList = getModel();
        if (modelList.containsKey(genre)) {
            if (modelList.get(genre).contains(track)) {
                modelList.get(genre).remove(track);
                if (modelList.get(genre).size() == 0) {
                    modelList.remove(genre);
                }
                saveModel(modelList);
                result = track.getTitle() + "(" + track.getArtist() + ", " + track.getAlbum() +  ", " + track.getLength() + ")" + ".Удален";
            } else {
                result = "Такого трека не существует!";
            }
        } else {
            result = "Нет жанра с таким названием!";
        }
    }

    public void getGenreList() throws IOException, ClassNotFoundException {
        Integer number = 1;
        modelList = getModel();
        result = "Текущая музыкальная библиотека:\n";
        if (modelList.size() == 0) {
            result += "Библиотека пуста";
        } else {
            for (Map.Entry<Genre, ArrayList<Track>> entry : modelList.entrySet()) {
                Integer count = 1;
                result += number.toString() + ") " + entry.getKey().getName() + ":\n";
                for (Track t : entry.getValue()) {
                    result += "   " + count.toString() + ". " + t.getTitle() + "(" + t.getArtist() + ", " + t.getAlbum() + ", " + t.getLength() + ")\n";
                    count++;
                }
                number++;
            }
        }
    }

    public void mergeData(String path) throws IOException, ClassNotFoundException {
        try {
            modelList = getModel();
            if (pathToFile.equals(path)) {
                result = "Произошла ошибка! (Файл уже используется программой)";
                return;
            }
            pathToFile = path;
            TreeMap<Genre, ArrayList<Track>> anotherModelList = getModel();
            pathToFile = defaultPathToFile;
            if (anotherModelList.size() == 0) {
                throw new EOFException();
            }

            for (Map.Entry<Genre, ArrayList<Track>> entry : anotherModelList.entrySet()) {
                if (modelList.containsKey(entry.getKey())) {
                    for (Track t : entry.getValue()) {
                        if (!modelList.get(entry.getKey()).contains(t)) {
                            modelList.get(entry.getKey()).add(t);
                        }
                    }
                } else {
                    modelList.put(entry.getKey(), entry.getValue());
                }
            }
            saveModel(modelList);
            result = path + ".Добавлен";
        }
        catch (EOFException e) {
            result = "Произошла ошибка! (Файл пуст)";
        }
    }
}
