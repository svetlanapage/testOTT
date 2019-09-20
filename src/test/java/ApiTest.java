import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;


public class ApiTests {
    
    //Тест, проверяющий доступность метода GET hotels/api/suggestRequest и наличие в теле ответа на запрос важных параметров
    
    @Test()
    public void testGet() {
        //выносим в локальные переменные в начало метода. Локальными переменными будем делать параметры запроса GET, с которым будем работать.
        //в данном кейсе это url и query, но в дальшейм можно будет добавлять и дургие - limit, lang, locale... в зависимости от того, какие проверки будем добавлять в тест :)
        String url = "https://www.onetwotrip.com/_hotels/api/suggestRequest";
        String query = "Мос";

                given()
                        //передаем параметры запроса
                        .param("query", query)
                        .when()
                        //отправляем GET-запрос с заданными параметрами
                        .get(url)
                        .then()
                        //проверяем, что сервер возвращает нам на запрос код ответа 200
                        .statusCode(200)
                        //проверяем, что ответ сервера приходит в формате JSON
                        .contentType("application/json")
                        .assertThat()
                        //проверяем, что в полученном JSON в параметре error приходит null (чтобы этот параметр ни значил, раз он про ошибки - стоит проверить :) )
                        .body("error", equalTo(null))
                        //проверяем, что массив в result не пустой
                        .body("result", not(emptyArray()))
                        //проверяем, что размер массива = 3, т.е. что в нем приходит три структуры - города, аэропорты, отели
                        .body("result",hasSize(3))
                        //проверяем, что в массиме result есть город с id Москвы, чтобы убедиться, что поисковый запрос пользвоателя был принят и обработан :)
                        .body("result.city_id", hasItem("524901"));

                // эти проверки считаем достаточными. Структуры со списком городов, аэропортов и отелей наверняка используются и в других методах
                // поэтмоу их проверку лучше вынести в отдельные методы, которые будут вызываться при необходимости.

    }

    @Test()
    public void testRedirect() {

        //Тест на проверку редиректа с onetwotrip.com происходит редирект на www.onetwotrip.com
        
        given()
                //в хедеры GET запроса передаем url youdo.ru и предпочтительную локаль - английску
                .param("url", "wotrip.com")
                .param("Accept-Language","en")
                .expect()
                //ожидаем получить в ответ на GET запрос код ошибки 301
                .statusCode(301)
                //а еще ожидаем получить в ответе от сервера url для редиректа в respons header, в параметре location
                .header("Location", is("www.onetwotrip.com"))
                .when()
                //отправляем GET запрос, если все ок, все ожидания оправдались (получили 301 и верный url в location), код будет исполняться дальше :)
                .get();
                //выполняем переход на url, полученный в location, ожидаем получить при переходе по нему 200
                when().get("www.onetwotrip.com").statusCode(200);
    }

}


