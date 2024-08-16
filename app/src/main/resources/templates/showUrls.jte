@import hexlet.code.dto.UrlsPage
@param  UrlsPage page

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
    @if(page.getUrls().isEmpty())
        <p>Пока не добавлен ни один сайт</p>
    @else
        @for(var url : page.getUrls())
            <div>
                <p>${url.getName()}</p>
            </div>
        @endfor
    @endif
</div>
</body>
</html>