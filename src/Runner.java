import interfaces.Folder;
import interfaces.MultiFolder;

import java.util.Arrays;

public class Runner {

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
