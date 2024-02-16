package br.com.dev.program;

import br.com.dev.entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Program {
    public static void main(String[] args) {

        System.out.println("Entre o caminho do arquivo");
        //Scanner sc = new Scanner(System.in);
        String path = "/home/fjunior/temp/desafio_17/in.csv";//sc.nextLine();

        try(BufferedReader br = new BufferedReader(new FileReader(path))){

            List<Sale> sales = new ArrayList<>();
            String line = br.readLine();
            while (line != null){
                String[] fields = line.split(",");
                sales.add(new Sale(Integer.valueOf(fields[0]), Integer.valueOf(fields[1]), fields[2], Integer.valueOf(fields[3]), Double.valueOf(fields[4])));
                line = br.readLine();
            }

            Comparator<Sale> comp = (pm1, pm2) -> pm1.averagePrice().compareTo(pm2.averagePrice());

            List<Sale> primeiras5Vendas = sales.stream()
                    .filter(s -> (s.getYear() == 2016 ))
                    .sorted(comp.reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            System.out.println("\nCinco primeiras vendas de 2016 de maior preço médio:");
            primeiras5Vendas.forEach(System.out::println);

            double valorTotal = sales.stream()
                    .filter(s -> s.getSeller().equals("Logan"))
                    .filter(s -> (s.getMonth() == 1 || s.getMonth() == 7))
                    .map(s -> s.getTotal())
                    .reduce(0.0, (x, y) -> x + y);

            System.out.println("\nValor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", valorTotal));


        }
        catch (IOException e) {
            System.err.println("ERROR: " + path + " (O sistema não pode encontrar o arquivo especificado)");
        }
    }
}