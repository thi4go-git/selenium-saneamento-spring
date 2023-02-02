package net.cloudtecnologia.client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Chrome {

    private static String URL_LOGIN = "http://wlcontabilidade.sytes.net:8080/wlgestao/login.jsf";
    private static String URL_SANEAMENTO_PRODUTOS = "http://wlcontabilidade.sytes.net:8080/wlgestao/paginas/saneamento/commom/produtossaneamento.jsf";
    private static String USER = "anilson-asj";
    private static String PASS = "115200";


    public WebDriver retornarNaveghador() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\thiago.melo\\Downloads\\chromedriver.exe");
        WebDriver navegador = new org.openqa.selenium.chrome.ChromeDriver();
        navegador.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
        return navegador;
    }

    public WebDriver logarWlGestao(WebDriver navegador) {
        navegador.get(URL_LOGIN);
        navegador.findElement(By.xpath("//*[@id=\"j_username\"]")).sendKeys(USER);
        navegador.findElement(By.xpath("//*[@id=\"j_password\"]")).sendKeys(PASS);
        navegador.findElement(By.xpath("//*[@id=\"j_idt15\"]")).click();
        return navegador;
    }

    public WebDriver acessarSaneamentoDeProdutos(WebDriver navegador) {
        navegador.get(URL_SANEAMENTO_PRODUTOS);
        return navegador;
    }

    public void paginarTabela(WebDriver navegador) {

        Scanner ler = new Scanner(System.in);
        System.out.println("Pressione 'ENTER' para começar a paginação!");
        ler.next();
        int totalRegistros = 0;
        int max = 4644;
        for (int i = 1; i <= max; i++) {
            String info = navegador.findElement(By.xpath("//*[@id=\"formSaneamento:listaProdutosEmpresa_paginator_top\"]/span[1]")).getText();
            System.out.println("Processando HTML:");
            System.out.println(info);
            //
            List<WebElement> lista = navegador.findElement(By.className("ui-datatable-tablewrapper"))
                    .findElements(By.tagName("tr"));
            System.out.println("");
            System.out.println("  INFO: ");
            System.out.println("");
            int cont = 0;
            for (WebElement elemento : lista) {
                if (cont > 0) {
                    totalRegistros++;
                    List<WebElement> colunas = elemento.findElements(By.tagName("td"));
                    //
                    try {
                        String linha = "BARRA:" + colunas.get(4).getText() + " # DESCRIÇÃO:" + colunas.get(5).getText() +
                                " # NCM:" + colunas.get(6).getText() + " # CST: " + colunas.get(7).getText() +
                                " # ALIQ C: " + colunas.get(8).getText() + " # ALIQ R: " + colunas.get(9).getText();
                        System.out.println(totalRegistros + " | " + linha);
                    } catch (Exception e) {
                        System.out.println("***Erro***");
                    }
                }
                cont++;
            }
            navegador.findElement(By.xpath("//*[@id=\"formSaneamento:listaProdutosEmpresa_paginator_top\"]/a[3]")).click();
            System.out.println("");
            System.out.println("Clicou na ABA: " + (i + 1));
            System.out.println("");
            System.out.println("---------------------------------------------------------------");
            System.out.println("");
            aguardarSeconds(10);
        }
    }


    public void aguardarSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}