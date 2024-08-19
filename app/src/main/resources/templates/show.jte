@import hexlet.code.utils.NamedRoutes
@import hexlet.code.dto.UrlPage
@import java.time.format.DateTimeFormatter
@param UrlPage page

!{var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

@template.layout.page(

content = @`
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
`,
)
