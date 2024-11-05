package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Sale> sales = new ArrayList<>();

            String line = br.readLine();

            while (line != null) {
                String[] fiels = line.split(",");
                int month = Integer.parseInt(fiels[0]);
                int year = Integer.parseInt(fiels[1]);
                String seller = fiels[2];
                int items = Integer.parseInt(fiels[3]);
                double total = Double.parseDouble(fiels[4]);

                sales.add(new Sale(month, year, seller, items, total));

                line = br.readLine();
            }

            List<Sale> top5AveragePrices = sales.stream()
                    .filter(sale -> sale.getYear() == 2016)  // Filtra as vendas de 2016
                    .sorted(Comparator.comparingDouble(Sale::averagePrice).reversed())  // Ordena os preços médios em ordem decrescente
                    .limit(5)  // Limita aos 5 primeiros
                    .toList();  // Coleta os resultados em uma lista

            System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
            top5AveragePrices.forEach(System.out::println);
            System.out.println();

            double totalVendido = sales.stream()
                    .filter(sale -> sale.getSeller().equals("Logan"))  // Filtra as vendas do vendedor Logan
                    .filter(sale -> sale.getMonth() == 1 || sale.getMonth() == 7)  // Filtra as vendas dos meses 1 (janeiro) e 7 (julho)
                    .mapToDouble(Sale::getTotal)  // Converte cada venda para o valor total (total)
                    .sum();  // Soma os valores totais

            System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f%n", totalVendido);


        } catch (Exception e) {
            System.out.println("Erro: " + path + " (O sistema não pode encontrar o arquivo especificado)");
        }


    }
}
