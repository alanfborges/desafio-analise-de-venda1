package com.alandev.application;

import com.alandev.entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Sale> list = new ArrayList<>();

        System.out.print("Entre com o caminho do arquivo: ");
        String path = sc.nextLine();
        System.out.println();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                Integer month = Integer.parseInt(fields[0]);
                Integer year = Integer.parseInt(fields[1]);
                String seller = fields[2];
                Integer items = Integer.parseInt(fields[3]);
                Double total = Double.parseDouble(fields[4]);

                list.add(new Sale(month, year, seller, items, total));
                line = br.readLine();
            }

            List<Sale> sales = list.stream()
                    .filter(sale -> sale.getYear().equals(2016))
                    .sorted(Comparator.comparingDouble(value -> -value.averagePrice()))
                    .limit(5)
                    .collect(Collectors.toList());

            System.out.println("Cinco primeiras vendas de 2016 de maior preço médio: ");
            sales.forEach(System.out::println);

            Double valorTotalMes1 = list.stream()
                    .filter(sale -> sale.getSeller().equals("Logan") && sale.getMonth().equals(1))
                    .map(sale -> sale.getTotal())
                    .reduce(0.0, (x, y) -> x + y);

            Double valorTotalMes7 = list.stream()
                    .filter(sale -> sale.getSeller().equals("Logan") && sale.getMonth().equals(7))
                    .map(sale -> sale.getTotal())
                    .reduce(0.0, (x, y) -> x + y);

            double value = valorTotalMes1 + valorTotalMes7;
            System.out.println();
            System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f%n", value);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
}
