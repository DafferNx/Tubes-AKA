import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class MainGUI extends JFrame {

    public static long jumlahKuadratIteratif(long n){
        long jumlah = 0;
        for (int i = 1; i <= n; i++) {
            jumlah += (long) i * i;
        }
        return jumlah;
    }

    public static long jumlahKuadratRekursif(long n){
        if(n == 0) return 0;
        return (long) n * n + jumlahKuadratRekursif(n - 1);
    }

    public MainGUI() {
        setTitle("Analisis Perbandingan Penjumlahan Kuadrat Pada Algoritma Iteratif vs Rekursif");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] kolom = {
            "n",
            "Hasil Penjumlahan Kuadrat",
            "Waktu Iteratif (ns)",
            "Waktu Rekursif (ns)"
        };

        DefaultTableModel model = new DefaultTableModel(kolom, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        long[] nTest = {10, 100, 1000, 2000, 5000, 10000};
        int jumRata = 10;

        for (long n : nTest) {
            long waktuIteratif = 0;
            long waktuRekursif = 0;
            long hasil = 0;

            for (int i = 0; i < jumRata; i++) {
                long waktuAwal = System.nanoTime();
                hasil = jumlahKuadratIteratif(n);
                waktuIteratif += System.nanoTime() - waktuAwal;

                waktuAwal = System.nanoTime();
                jumlahKuadratRekursif(n);
                waktuRekursif += System.nanoTime() - waktuAwal;
            }

            long rataIteratif = waktuIteratif / jumRata;
            long rataRekursif = waktuRekursif / jumRata;

            model.addRow(new Object[]{
                n,
                hasil,
                rataIteratif,
                rataRekursif
            });

            dataset.addValue(rataIteratif, "Iteratif", String.valueOf(n));
            dataset.addValue(rataRekursif, "Rekursif", String.valueOf(n));
        }

        JFreeChart chart = ChartFactory.createLineChart(
            "Perbandingan Waktu Eksekusi",
            "Nilai n",
            "Waktu (nanosecond)",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);

        JSplitPane splitPane = new JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            scrollPane,
            chartPanel
        );

    splitPane.setDividerLocation(200);   // tinggi awal tabel
    splitPane.setResizeWeight(0.3);      // 30% tabel, 70% chart

    add(splitPane, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
    }
}
