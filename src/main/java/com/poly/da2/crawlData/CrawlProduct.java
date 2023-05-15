package com.poly.da2.crawlData;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.da2.config.AppConfig;
import com.poly.da2.entity.Category;
import com.poly.da2.model.Data;
import com.poly.da2.model.Images;
import com.poly.da2.model.Product;
import com.poly.da2.repository.CategoryRepository;
import com.poly.da2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrawlProduct {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public static <T> T crawl(String apiURl, Class<T> targetType) {
        try {
            URL url = new URL(apiURl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            T object = gson.fromJson(response.toString(), targetType);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  void inserData(){
        String apiUrl = "https://tiki.vn/api/v2/products?limit=40&include=advertisement&aggregations=2&trackity_id=8ef46662-b3e5-ad66-1a3c-43e61b5979d5&q=laptop&category=21492&page=1";
        Data data = crawl(apiUrl, Data.class);
        List<Product> products = data.getData();
        String url;
        List<Product> products1 = new ArrayList<>();
        for(Product p: products){
            url ="https://tiki.vn/api/v2/products/"+p.getId()+"?platform=web&spid=185210955";
            Product product = crawl(url, Product.class);
            products1.add(product);
        }

        for (Product p: products1){
            List<Images> images = p.getImages();
            StringBuilder sb = new StringBuilder();
            for(Images i : images){
                sb.append(i.getBase_url()+",");
            }

            sb.deleteCharAt(sb.length()-1);
            Category category = new Category();
            category.setId("cate6");
            category.setName("Túi xách và ba lô");
            categoryRepository.save(category);

            com.poly.da2.entity.Product entiy = new com.poly.da2.entity.Product();
            entiy.setName(p.getName());
            entiy.setCategory(category);
            entiy.setDescription(p.getShort_description());
            entiy.setPrice(p.getPrice());
            entiy.setRating_average(p.getRating_average());
            entiy.setReview_count(p.getReview_account());
            entiy.setImage_urls(sb.toString());
            entiy.setThumbnail_url(p.getThumbnail_url());

            productRepository.save(entiy);
        }
    }

}
