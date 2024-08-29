@import hexlet.code.utils.NamedRoutes
@import hexlet.code.dto.UrlPage
@import java.time.format.DateTimeFormatter
@param UrlPage page

!{var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

@template.layout.page(

content = @`
    <div class="container" style="margin-top: 2rem;">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Имя</th>
                <th scope="col">Дата добавления</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th scope="row">${page.getUrl().getId()}</th>
                <td>${page.getUrl().getName()}</td>
                <td>${page.getUrl().getCreatedAt().toLocalDateTime().format(formatter)}</td>
            </tr>
            </tbody>
        </table>

        <form class="row g-1" action="${NamedRoutes.urlCheck(page.getUrl().getId())}" method="post" style="margin-top: 15px">
            <div class="input-group">
                <button type="submit" class="btn btn-secondary">Проверить</button>
            </div>
        </form>

        <table class="table">
            <thead>
            <tr>
                <th scope="col">№</th>
                <th scope="col">Код ответа</th>
                <th scope="col">title</th>
                <th scope="col">h1</th>
                <th scope="col">description</th>
                <th scope="col">Дата проверки</th>
            </tr>
            </thead>
            <tbody>
            @for(var check : page.getUrlCheck())
                <tr>
                    <td>${check.getId()}</td>
                    <td>${check.getStatusCode()}</td>
                    <td>${check.getTitle()}</td>
                    <td>${check.getH1()}</td>
                    <td>${check.getDescription()}</td>
                    <td>${check.getCreatedAt().toLocalDateTime().format(formatter)}</td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`
)
