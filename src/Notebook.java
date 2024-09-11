import java.util.*;

public class Notebook {
    private String model;
    private int ram;
    private int hddCapacity;
    private String os;
    private String color;

    public Notebook(String model, int ram, int hddCapacity, String os, String color) {
        this.model = model;
        this.ram = ram;
        this.hddCapacity = hddCapacity;
        this.os = os;
        this.color = color;
    }

    // Getters and Setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getHddCapacity() {
        return hddCapacity;
    }

    public void setHddCapacity(int hddCapacity) {
        this.hddCapacity = hddCapacity;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Модель: " + model + ", RAM: " + ram + "GB, HDD: " + hddCapacity + "ГБ, ОС: " + os + ", цвет: " + color;
    }
}

class CriteriaFilter {
    private final Map<Integer, String> criteriaMap = new HashMap<>();
    private final Map<Integer, Integer> minValuesMap = new HashMap<>();

    public void addCriterion(int key, String value) {
        criteriaMap.put(key, value);
    }

    public void setMinValue(int key, int value) {
        minValuesMap.put(key, value);
    }

    public boolean meetsCriteria(Notebook notebook) {
        for (var entry : criteriaMap.entrySet()) {
            switch (entry.getKey()) {
                case 1:
                    if (!meetsCriterion("RAM", notebook.getRam(), minValuesMap.getOrDefault(1, 0))) {
                        return false;
                    }
                    break;
                case 2:
                    if (!meetsCriterion("HDD Capacity", notebook.getHddCapacity(), minValuesMap.getOrDefault(2, 0))) {
                        return false;
                    }
                    break;
                case 3:
                    if (!meetsCriterion("OS", notebook.getOs(), minValuesMap.getOrDefault(3, ""))) {
                        return false;
                    }
                    break;
                case 4:
                    if (!meetsCriterion("Color", notebook.getColor(), minValuesMap.getOrDefault(4, ""))) {
                        return false;
                    }
                    break;
            }
        }

        return true;
    }

    private static boolean meetsCriterion(String criterionName, Object value, Object minimumValue) {
        if (minimumValue instanceof Integer) {
            return ((Integer)value >= (Integer)minimumValue);
        } else {
            return ((String)value).equals((String)minimumValue);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        List<Notebook> laptopList = Arrays.asList(
                new Notebook("Lenovo ThinkPad X1 Carbon Gen 9", 8, 512, "Windows 10 Pro", "Black"),
                new Notebook("Dell XPS 13 OLED", 16, 1TB, "Windows 11 Home", "White"),
                new Notebook("Apple Macbook Air M1", 8, 256, "macOS Monterey", "Silver")
        );


        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите цифру, соответствующую необходимому критерию:");
        System.out.print("1 - ОЗУ\n2 - Объем ЖД\n3 - Операционная система\n4 - Цвет\nВаш выбор: ");
        int choice = scanner.nextInt();
        while (choice < 1 || choice > 4) {
            System.err.println("Неверный выбор. Пожалуйста, введите число от 1 до 4.");
            System.out.printf("Выбор: %d\n", choice);
            choice = scanner.nextInt();
        }

        switch (choice) {
            case 1:
                System.out.printf("\nМинимальное значение ОЗУ: ");
                int minRam = scanner.nextInt();
                CriteriaFilter filter = new CriteriaFilter();
                filter.addCriterion(1, "RAM");
                filter.setMinValue(1, minRam);
                break;
            case 2:
                System.out.printf("\nМинимальный объем жесткого диска: ");
                int minHddCapacity = scanner.nextInt();
                CriteriaFilter filter2 = new CriteriaFilter();
                filter2.addCriterion(2, "HDD Capacity");
                filter2.setMinValue(2, minHddCapacity);
                break;
            case 3:
                System.out.printf("\nОперационная система: ");
                String minOs = scanner.next();
                CriteriaFilter filter3 = new CriteriaFilter();
                filter3.addCriterion(3, "OS");
                filter3.setMinValue(3, minOs);
                break;
            default:
                System.out.printf("Цвет: ");
                String minColor = scanner.next();
                CriteriaFilter filter4 = new CriteriaFilter();
                filter4.addCriterion(4, "Color");
                filter4.setMinValue(4, minColor);
        }

        List<Notebook> filteredLaptops = new ArrayList<>();
        for (Notebook laptop : laptopList) {
            if (filter != null && !filter.meetsCriteria(laptop)) continue;
            if (filter2 != null && !filter2.meetsCriteria(laptop)) continue;
            if (filter3 != null && !filter3.meetsCriteria(laptop)) continue;
            if (filter4 != null && !filter4.meetsCriteria(laptop)) continue;

            filteredLaptops.add(laptop);
        }

        for (Notebook laptop : filteredLaptops) {
            System.out.println(laptop);
        }
    }
}
