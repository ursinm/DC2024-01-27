using System.Text;

using Microsoft.AspNetCore.Mvc;


namespace Publisher.Controllers;

[Route("api/v1.0/[controller]")]
[ApiController]
public class PostsController : ControllerBase
{
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IConfiguration _configuration;

    public PostsController(IHttpClientFactory httpClientFactory, IConfiguration configuration)
    {
        _httpClientFactory = httpClientFactory;
        _configuration = configuration;
    }

    [HttpGet]
    public async Task<IActionResult> GetAll()
    {
        // Создание клиента HttpClient
        var httpClient = _httpClientFactory.CreateClient();

        // Формирование URL для перенаправления
        var redirectUrl = _configuration["Discussion:Url"];
        var newRequest = new HttpRequestMessage(HttpMethod.Get, redirectUrl);

        var requestHeaders = Request.Headers;
        foreach (var (key, value) in requestHeaders)
        {
            if (string.Equals(key, "Content-Length", StringComparison.OrdinalIgnoreCase))
                continue;
            // Пропускаем заголовок 'Content-Type', так как он будет добавлен к содержимому
            if (string.Equals(key, "Content-Type", StringComparison.OrdinalIgnoreCase))
                continue;
            newRequest.Headers.Add(key, value.ToArray());
        }
        // Отправка запроса на новый адрес
        var response = await httpClient.SendAsync(newRequest);

        // Проверка статуса ответа и возврат результата
        if (response.IsSuccessStatusCode)
        {
            // Получаем содержимое ответа
            string responseBody = await response.Content.ReadAsStringAsync();
            // Возвращаем ответ как результирующий контент
            return Ok(responseBody);
        }
        else
        {
            // Если запрос неуспешен, возвращаем статус ответа и сообщение об ошибке
            return StatusCode((int)response.StatusCode, $"Error: {response.StatusCode}");
        }

    }
    
    [HttpGet("{id}")]
    public async Task<ActionResult> GetUserById([FromRoute]long id)
    {
        var httpClient = _httpClientFactory.CreateClient();

        // Формирование URL для перенаправления
        var redirectUrl = _configuration["Discussion:Url"] + "/"+id;

        // Отправка запроса на новый адрес
        var newRequest = new HttpRequestMessage(HttpMethod.Get, redirectUrl);

        var requestHeaders = Request.Headers;
        foreach (var (key, value) in requestHeaders)
        {
            if (string.Equals(key, "Content-Length", StringComparison.OrdinalIgnoreCase))
                continue;
            // Пропускаем заголовок 'Content-Type', так как он будет добавлен к содержимому
            if (string.Equals(key, "Content-Type", StringComparison.OrdinalIgnoreCase))
                continue;
            newRequest.Headers.Add(key, value.ToArray());
        }
        // Отправка запроса на новый адрес
        var response = await httpClient.SendAsync(newRequest);

        // Проверка статуса ответа и возврат результата
        if (response.IsSuccessStatusCode)
        {
            // Получаем содержимое ответа
            string responseBody = await response.Content.ReadAsStringAsync();
            // Возвращаем ответ как результирующий контент
            return Ok(responseBody);
        }
        else
        {
            // Если запрос неуспешен, возвращаем статус ответа и сообщение об ошибке
            return StatusCode((int)response.StatusCode, $"Error: {response.StatusCode}");
        }


    }

    // POST: api/v1.0/User
    [HttpPost]
    public async Task<ActionResult> CreatePost()
    {
        var httpClient = _httpClientFactory.CreateClient();
    
        // Получаем тело запроса
        string requestBody;
        using (StreamReader reader = new StreamReader(HttpContext.Request.Body, Encoding.UTF8))
        {
            requestBody = await reader.ReadToEndAsync();
        }
    
        // Формирование URL для перенаправления
        var redirectUrl = _configuration["Discussion:Url"];;
        var newrequestBody = new StringContent(requestBody, Encoding.UTF8, "application/json");

        // Создание нового запроса
        var newRequest = new HttpRequestMessage(HttpMethod.Post, redirectUrl);
        newRequest.Content = newrequestBody;

        // Добавляем заголовки из исходного запроса в новый запрос
        foreach (var (key, value) in Request.Headers)
        {
            if (string.Equals(key, "Content-Length", StringComparison.OrdinalIgnoreCase))
                continue;
            // Пропускаем заголовок 'Content-Type', так как он будет добавлен к содержимому
            if (string.Equals(key, "Content-Type", StringComparison.OrdinalIgnoreCase))
                continue;

            newRequest.Headers.Add(key, value.ToArray());
        }

        // Отправка запроса на новый адрес
        var response = await httpClient.SendAsync(newRequest);

        if (response.IsSuccessStatusCode)
        {
            // Получаем содержимое ответа
            string responseBody = await response.Content.ReadAsStringAsync();
            // Возвращаем ответ как результирующий контент
            return StatusCode((int)response.StatusCode, responseBody);
        }
        else
        {
            // Если запрос неуспешен, возвращаем статус ответа и сообщение об ошибке
            return StatusCode((int)response.StatusCode, $"Error: {response.StatusCode}");
        }
        // Проверка статуса ответа и возврат результата
        
    }

    // PUT: api/v1.0/User/5
    [HttpPut]
    public async Task<ActionResult> UpdatePost()
    {
        var httpClient = _httpClientFactory.CreateClient();
    
        // Получаем тело запроса
        string requestBody;
        using (StreamReader reader = new StreamReader(HttpContext.Request.Body, Encoding.UTF8))
        {
            requestBody = await reader.ReadToEndAsync();
        }
    
        // Формирование URL для перенаправления
        var redirectUrl = _configuration["Discussion:Url"];;
        var newrequestBody = new StringContent(requestBody, Encoding.UTF8, "application/json");

        // Создание нового запроса
        var newRequest = new HttpRequestMessage(HttpMethod.Put, redirectUrl);
        newRequest.Content = newrequestBody;

        // Добавляем заголовки из исходного запроса в новый запрос
        foreach (var (key, value) in Request.Headers)
        {
            if (string.Equals(key, "Content-Length", StringComparison.OrdinalIgnoreCase))
                continue;
            // Пропускаем заголовок 'Content-Type', так как он будет добавлен к содержимому
            if (string.Equals(key, "Content-Type", StringComparison.OrdinalIgnoreCase))
                continue;

            newRequest.Headers.Add(key, value.ToArray());
        }

        // Отправка запроса на новый адрес
        var response = await httpClient.SendAsync(newRequest);

        // Проверка статуса ответа и возврат результата
        if (response.IsSuccessStatusCode)
        {
            // Получаем содержимое ответа
            string responseBody = await response.Content.ReadAsStringAsync();
            // Возвращаем ответ как результирующий контент
            return StatusCode((int)response.StatusCode, responseBody);
        }
        else
        {
            // Если запрос неуспешен, возвращаем статус ответа и сообщение об ошибке
            return StatusCode((int)response.StatusCode, $"Error: {response.StatusCode}");
        }
        
    }

    // DELETE: api/v1.0/User/5
    [HttpDelete("{id}")]
    public async Task<ActionResult> DeletePost(long id)
    {
        var httpClient = _httpClientFactory.CreateClient();

        // Формирование URL для перенаправления
        var redirectUrl = _configuration["Discussion:Url"] + "/"+id;

        // Отправка запроса на новый адрес
        var newRequest = new HttpRequestMessage(HttpMethod.Delete, redirectUrl);

        foreach (var (key, value) in Request.Headers)
        {
            if (string.Equals(key, "Content-Length", StringComparison.OrdinalIgnoreCase))
                continue;
            // Пропускаем заголовок 'Content-Type', так как он будет добавлен к содержимому
            if (string.Equals(key, "Content-Type", StringComparison.OrdinalIgnoreCase))
                continue;

            newRequest.Headers.Add(key, value.ToArray());
        }
        // Отправка запроса на новый адрес
        var response = await httpClient.SendAsync(newRequest);

        // Проверка статуса ответа и возврат результата
        if (response.IsSuccessStatusCode)
        {
            // Получаем содержимое ответа
            string responseBody = await response.Content.ReadAsStringAsync();
            // Возвращаем ответ как результирующий контент
            return StatusCode((int)response.StatusCode, responseBody);
        }
        else
        {
            // Если запрос неуспешен, возвращаем статус ответа и сообщение об ошибке
            return StatusCode((int)response.StatusCode, $"Error: {response.StatusCode}");
        }

        
    }
}