package com.kfa.bank;

import com.kfa.bank.dao.CustomFileDao;
import com.kfa.bank.dao.CustomFolderDao;
import com.kfa.bank.exception.CustomFileTransactionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScanFiles {

    private static List<File> allFiles;
    private static List<File> allDirs;
    private static String PATH_TO_SCAN = "E:\\Music\\mp3";

    @Autowired
    private static CustomFileDao customFileDao;

    @Autowired
    private static CustomFolderDao customFolderDao;
    //private static String PATH_TO_SCAN = "E:\\Music\\mp3\\Classic";


    //Iterator iterFiles =

    public static void main(String[] args) throws IOException {

        allFiles = new ArrayList<File>();
        allDirs = new ArrayList<File>();

        /*
        File dirToScan = new File(PATH_TO_SCAN);
        File[] files = dirToScan.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                allDirs.add(file);
            } else {
                allFiles.add(file);
            }
        }
        */
        scanFiles(PATH_TO_SCAN);
        //read(allDirs);
        System.out.println("-------------------");
        read(allFiles);
        System.out.println("-------------------");
        System.out.println("Nombre de dossiers scannés: " + allDirs.size());
        System.out.println("Nombre de fichiers scannés: " + allFiles.size());
    }

    private static void scanFiles(String aPath) throws IOException {

        // insert root folder in base
        File rootFolder = new File(PATH_TO_SCAN);
        //String rootFolderName = rootFolder.getParentFile().getName();

        //saveFolder(rootFolder.getName(), -1, rootFolder.getName() , 10, null, null);

        DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(aPath));
        for (Path path : dirStream) {
            //System.out.println(path.getFileName());
            File file = path.toFile();
            String filename = file.getName();
            int folderId = -1;
            if (file.isDirectory()) {
                allDirs.add(file);
                //File [] subFiles = file.listFiles();
                //System.out.println(file.getPath());
                // TODO : insert in DB
                Date creationDate = new Date();
                Date updateDate = null;
                File parentFile = file.getParentFile();
                String foldername = parentFile.getName();

                // Chercher le folder en base, s'il n'existe pas on le crée
                //saveFolder(filename, idParent,folderPath, creationDate, updateDate);

                scanFiles(file.getPath());
            } else {

                String extension = ".txt";
                String filePath = file.getPath();
                int size = (int) file.length();
                // donnera "txt".
                Date creationDate = new Date();
                Date updateDate = null;
                saveFile(filename, folderId, extension, filePath, size, creationDate, updateDate);
                allFiles.add(file);
            }
        }

        //file-> file.toString()).forEach(System.out::println);

        //.toString()).forEach(System.out::println);
        //.toString().).forEach(System.out::println);
        //path-> path.toString()).forEach(System.out::println)));A
    }
    private static void saveFile(String filename, int folderId, String extension, String path, int size, Date creationDate, Date updateDate) {
        try {

            customFileDao.saveFile(filename, extension, folderId, size, path, creationDate, updateDate);

        } catch (
                CustomFileTransactionException e) {
            //model.addAttribute("errorMessage", "Error: " + e.getMessage());
        }
    }

    private static void saveFolder(String folderName, int idParent, String folderPath, int size, Date creationDate, Date updateDate) {
        try {

            customFolderDao.saveFolder(folderName, idParent, folderPath, size, creationDate, updateDate);

        } catch (
                CustomFileTransactionException e) {
            //model.addAttribute("errorMessage", "Error: " + e.getMessage());
        }
    }

    private static void read(List<File> aList) {
        for (File file : aList) {
            long fileSize = file.getTotalSpace();
            System.out.println(file.getName());
            //+ "| "+ fileSize);
        }
    }

}
