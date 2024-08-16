@import hexlet.code.utils.NamedRoutes
@import  hexlet.code.dto.BuildUrlsPage
@param BuildUrlsPage page


<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <title>Анализатор страниц</title>
</head>
<body>
    <div>
        <p>Тут будет главная страница</p>
        @if(page.getErrors() != null)
            <ul>
                @for(var validator : page.getErrors().values())
                    @for(var error : validator)
                        <li>${error.getMessage()}</li>
                    @endfor
                @endfor
            </ul>
        @endif
        <form action="${NamedRoutes.urlsPath()}" method="post" style="margin-top: 15px">
            <div class="mb-3">
                <label>
                    <input type="url" class="form-control" placeholder="Введите адрес" name="name" value="${page.getName()}"/>
                </label>
            </div>
            <input type="submit" class="btn btn-primary mb-3" value="Проверить"/>
        </form>
    </div>
</body>
</html>