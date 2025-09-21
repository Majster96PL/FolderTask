import interfaces.Cabinet;
import interfaces.Folder;
import interfaces.MultiFolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet {
    private final List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return findFolderByName(name, folders);
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        List<Folder> result = new ArrayList<>();
        findFoldersBySize(size, folders, result);
        return result;
    }

    @Override
    public int count() {
        return countFolders(folders);
    }

    private Optional<Folder> findFolderByName(String name, List<Folder> folders) {
        for (Folder folder : folders) {
            if (folder.getName().equals(name)) {
                return Optional.of(folder);
            }
            if (folder instanceof MultiFolder) {
                Optional<Folder> found = findFolderByName(name, ((MultiFolder) folder).getFolders());
                if (found.isPresent()) return found;
            }
        }
        return Optional.empty();
    }

    private void findFoldersBySize(String size, List<Folder> folders, List<Folder> result) {
        for (Folder folder : folders) {
            if (folder.getSize().equals(size)) {
                result.add(folder);
            }
            if (folder instanceof MultiFolder) {
                findFoldersBySize(size, ((MultiFolder) folder).getFolders(), result);
            }
        }
    }

    private int countFolders(List<Folder> folders) {
        int count = 0;
        for (Folder folder : folders) {
            count++;
            if (folder instanceof MultiFolder) {
                count += countFolders(((MultiFolder) folder).getFolders());
            }
        }
        return count;
    }

    private static FileCabinet getFileCabinet() {
        Folder f1 = new SimpleFolder("Invoices", "SMALL");
        Folder f2 = new SimpleFolder("Projects", "MEDIUM");
        Folder f3 = new SimpleFolder("Personal", "SMALL");

        Folder sub1 = new SimpleFolder("Sub-A", "SMALL");
        Folder sub2 = new SimpleFolder("Sub-B", "LARGE");
        Folder sub3 = new SimpleFolder("Sub-C", "MEDIUM");

        MultiFolder mf1 = new ComplexFolder("Archive", "MEDIUM", Arrays.asList(sub1, sub2));
        MultiFolder mf2 = new ComplexFolder("Work", "LARGE", Arrays.asList(f2, mf1, sub3));

        return new FileCabinet(Arrays.asList(f1, f3, mf2));
    }

    public static void main(String[] args) {
        FileCabinet cabinet = getFileCabinet();

        System.out.println("🔍 Folder 'Sub-B':");
        System.out.println(cabinet.findFolderByName("Sub-B").orElse(null));

        System.out.println("\n Count of all directories 'SMALL':");
        for (Folder folder : cabinet.findFoldersBySize("SMALL")) {
            System.out.println(folder);
        }

        System.out.println("\n Count of all directories 'SMALL':");
        System.out.println(cabinet.count());
    }
}
