import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main {

    public static long jumlahKuadratIteratif(long n){
        long jumlah = 0;
        for (int i = 1; i <= n; i++) {
            jumlah = jumlah + (long)i*i;
        }
        return jumlah;
    }

    public static long jumlahKuadratRekursif(long n){
        if(n == 0){
            return 0;
        }
        return (long) n*n + jumlahKuadratRekursif(n-1);
    }

    public static void main(String[] args) {
        long[] nTest = {10, 100, 1000, 2000, 5000, 10000, 20000};
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (long n : nTest) {
            long waktuAwal = System.nanoTime();
            long hasilIteratif = jumlahKuadratIteratif(n);
            long waktuIteratif = System.nanoTime() - waktuAwal;

            waktuAwal = System.nanoTime();
            long hasilRekursif = jumlahKuadratRekursif(n);
            long waktuRekursif = System.nanoTime() - waktuAwal;

            System.out.println("n = " + n);
            System.out.println("Iteratif : " + hasilIteratif + " | waktu = " + waktuIteratif + " ns");
            System.out.println("Rekursif : " + hasilRekursif + " | waktu = " + waktuRekursif + " ns");
            System.out.println();

            dataset.addValue(waktuIteratif, "Iteratif", String.valueOf(n));
            dataset.addValue(waktuRekursif, "Rekursif", String.valueOf(n));
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Perbandingan Waktu Eksekusi",
                "Nilai n",
                "Waktu (nanosecond)",
                dataset
        );

        ChartFrame frame = new ChartFrame("Grafik Kompleksitas", chart);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}