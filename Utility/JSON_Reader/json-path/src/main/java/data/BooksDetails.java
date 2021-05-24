package data;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.spi.json.JsonProvider;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jayway.jsonpath.JsonPath.*;
import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;
import static com.jayway.jsonpath.JsonPath.parse;

public class BooksDetails {

    private final static String PATH = "data/books.json";
    private static Object document;
    private static StringBuilder sb;
    private static Configuration configuration;

    //Predicates can be built using the Filter API
    Filter cheapFictionFilter = filter(
            where("category").is("fiction").and("price").lte(10D)
    );

    Filter fictionFilter = filter(
            where("category").eq("fiction")
    );

    Filter priceFilter_Grt_10D = filter(
            where("price").gt(10D)
    );

    //Predicates to get books - By the way Predicates are cool !
    List<Map<String, Object>> books_price_less_10 =  parse(document)
            .read("$.store.book[?(@.price < 10)]");

    List<Map<String, Object>> books_cheapFictionFilter =
            parse(document).read("$.store.book[?]", cheapFictionFilter);

    List<Map<String, Object>> books_Fiction_PriceGt10D =
            parse(document).read("$.store.book[?, ?]", fictionFilter,priceFilter_Grt_10D);


    //Roll Your Own
    //Third option is to implement your own predicates
    Predicate booksWithISBN = new Predicate() {
        @Override
        public boolean apply(PredicateContext ctx) {
            return ctx.item(Map.class).containsKey("isbn");
        }
    };

    List<Map<String, Object>> books_with_isbn =
            parse(document).read("$.store.book[?]", List.class, booksWithISBN);



    static {
        BufferedReader reader = null;
        sb = new StringBuilder();
        try {
            reader = Files.newBufferedReader(Paths.get(PATH));
            reader.lines().forEach(line -> sb.append(line + System.lineSeparator()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        document = Configuration.defaultConfiguration()
                .jsonProvider().parse(sb.toString());
        configuration = Configuration.defaultConfiguration();
    }

    /**
     * The simplest most straight forward way to use JsonPath is via the static read API
     *
     * @return
     */
    public List<String> getAuthorsList() {
        //We can pass Object of JSON in read method
        String json = sb.toString();
        return read(sb.toString(), "$.store.book[*].author");
    }

    /**
     * Get Specific author based on index
     *
     * @param index
     * @return
     */
    public String getAuthor(int index) {
        try {
            //We can pass file of json in read method
            return read(new File(PATH), "$.store.book[" + index + "].author");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAuthorsList_FluentAPI() {
        ReadContext ctx;
        try {
            //We can pass FileInputStream, String JSON , Object JSON, File JSON as per our choice
            ctx = parse(new FileInputStream(Paths.get(PATH).toFile()));
            return ctx.read("$.store.book[?(@.pages)].author");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, Object>> getBooks_FluentAPI() {
        /** When using JsonPath in java its important to know what type you expect in your result.
         JsonPath will automatically try to cast the result to the type expected by the invoker.*/
        return using(configuration) //Pass configuration type
                .parse(sb.toString()) // Pass String of JSON
                .read("$.store.book[?(@.price>10)]");
    }


    public static void main(String[] args) {

        BooksDetails booksDetails = new BooksDetails();

        //Get List of Authors
        System.out.println("Get List of Authors");
        System.out.println(booksDetails.getAuthorsList());

        //Get Specific author based on index
        System.out.println("Get author for index 1");
        System.out.println(booksDetails.getAuthor(1));
        System.out.println("Get author for index 3");
        System.out.println(booksDetails.getAuthor(3));

        //Get Author for books having page variable
        System.out.println("Get Author for books having page variable");
        System.out.println(booksDetails.getAuthorsList_FluentAPI());

        //Get whole books based on condition
        List<Map<String, Object>> books = booksDetails.getBooks_FluentAPI();
        books.stream()
                .forEach(bk -> System.out.println(bk.get("author")));

        //Evaluate the return type by MappingProvide
        String jsonString = "{\"date_as_long\" : 1411455611975}";
        Date date = parse(jsonString).read("$['date_as_long']", Date.class);
        System.out.println(date);

        //Get Books using predicates
        List<Map<String, Object>> books_price_less_10 = booksDetails.books_price_less_10;
        books_price_less_10.stream()
                .forEach(bk -> System.out.println(bk.get("author")));

        //Get Cheap Fiction Book
        List<Map<String, Object>> books_cheapFictionFilter = booksDetails.books_cheapFictionFilter;
        books_cheapFictionFilter.stream()
                .forEach(bk -> System.out.println(bk.get("author")));

        //Apply 2 filters
        System.out.println("Apply 2 filters");
        List<Map<String, Object>> books_Fiction_PriceGt10D = booksDetails.books_Fiction_PriceGt10D;
        books_Fiction_PriceGt10D.stream()
                .forEach(bk -> System.out.println(bk.get("author")));


        //Using own predicates
        System.out.println("Using own predicates");
        List<Map<String, Object>> books_with_isbn = booksDetails.books_with_isbn;
        books_with_isbn.stream()
                .forEach(bk -> System.out.println(bk.get("author")));


    }


}
