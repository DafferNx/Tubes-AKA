public class Main{

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
        
        long[] nTest = {10,100,1000,10000};
        long waktuAwal,waktuTotal;

        for (long n : nTest) {
            waktuAwal = System.nanoTime();
            jumlahKuadratIteratif(n);
            waktuTotal = (System.nanoTime() - waktuAwal);
            System.out.println("Waktu Iteratif pada n="+n+" : "+ waktuTotal + "ns");

            waktuAwal = System.nanoTime();
            jumlahKuadratRekursif(n);
            waktuTotal = (System.nanoTime() - waktuAwal);
            System.out.println("Waktu Rekursif pada n="+n+" : "+ waktuTotal + "ns");
        }

    }
}