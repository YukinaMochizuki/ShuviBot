package org.mochizuki.bot.unit;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;

public class PluginFileVisitor extends SimpleFileVisitor<Path> {
    private ArrayList<Path> pathArrayList = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if(file.getFileName().toString().endsWith(".jar")){
            pathArrayList.add(file);
        }
        return CONTINUE;
    }


    public ArrayList<Path> getPathArrayList() {
        return pathArrayList;
    }
}
