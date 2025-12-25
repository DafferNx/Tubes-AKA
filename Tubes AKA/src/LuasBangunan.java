import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class LuasBangunan extends JFrame {

    private JTextField txtPanjang, txtLebar, txtKeramik;
    private JTable table;
    private DefaultTableModel model;

    public LuasBangunan() {
        setTitle("Perhitungan Luas Bangunan Keramik");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelInput = new JPanel(new GridLayout(4, 2, 5, 5));

        panelInput.add(new JLabel("Panjang Keramik (cm):"));
        txtPanjang = new JTextField("");
        panelInput.add(txtPanjang);

        panelInput.add(new JLabel("Lebar Keramik (cm):"));
        txtLebar = new JTextField("");
        panelInput.add(txtLebar);

        panelInput.add(new JLabel("Jumlah Keramik (pisahkan koma):"));
        txtKeramik = new JTextField("10,100,1000,5000,10000");
        panelInput.add(txtKeramik);

        JButton btnHitung = new JButton("Hitung & Tampilkan");
        panelInput.add(btnHitung);

        JButton btnGrafik = new JButton("Tampilkan Grafik");
        panelInput.add(btnGrafik);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Jumlah Keramik",
                "Luas Bangunan Iteratif(m²)",
                "Waktu Iteratif (ns)",
                "Luas Bangunan Rekursif(m²)",
                "Waktu Rekursif (ns)"
        });

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(panelInput, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnHitung.addActionListener(e -> hitung());
        btnGrafik.addActionListener(e -> tampilkanGrafik());
    }

    public static double hitungLuasIteratif(long jumlahKeramik, double p, double l) {
        double luasKeramik = p * l;
        double total = 0;
        for (int i = 0; i < jumlahKeramik; i++) {
            total = total + luasKeramik;
        }
        return total;
    }

    public static double hitungLuasRekursif(long jumlahKeramik, double p, double l) {
        if (jumlahKeramik == 0) {
            return 0;
        }else{
            return (p * l) + hitungLuasRekursif(jumlahKeramik - 1, p, l);
        }
    }

    private void hitung() {
        model.setRowCount(0);

        double panjang = Double.parseDouble(txtPanjang.getText()) / 100.0;
        double lebar = Double.parseDouble(txtLebar.getText()) / 100.0;

        String[] data = txtKeramik.getText().split(",");

        for (String s : data) {
            long jumlah = Long.parseLong(s.trim());

            long start = System.nanoTime();
            double luasI = hitungLuasIteratif(jumlah, panjang, lebar);
            long waktuI = System.nanoTime() - start;

            start = System.nanoTime();
            double luasR = hitungLuasRekursif(jumlah, panjang, lebar);
            long waktuR = System.nanoTime() - start;

            model.addRow(new Object[]{
                jumlah,
                String.format("%.2f", luasI),
                waktuI,
                String.format("%.2f", luasR),
                waktuR
});

        }
    }

    private void tampilkanGrafik() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < model.getRowCount(); i++) {
            String n = model.getValueAt(i, 0).toString();
            long wI = (long) model.getValueAt(i, 2);
            long wR = (long) model.getValueAt(i, 4);

            dataset.addValue(wI, "Iteratif", n);
            dataset.addValue(wR, "Rekursif", n);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Perbandingan Waktu Eksekusi",
                "Jumlah Keramik",
                "Waktu (ns)",
                dataset
        );

        ChartFrame frame = new ChartFrame("Grafik Kompleksitas", chart);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LuasBangunan().setVisible(true));
    }
}
