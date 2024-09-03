@import hexlet.code.dto.UrlsPage
@import hexlet.code.utils.NamedRoutes
@import java.time.format.DateTimeFormatter
@param  UrlsPage page

!{var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

@template.layout.page(
content = @`
    @if(page.getFlash() != null)
        <div class="alert alert-${page.getFlashType()}" role="alert">
            ${page.getFlash()}
        </div>
    @endif
    <div class="container" style="margin-top: 2rem;">
        @if(page.getUrls().isEmpty())
            <p>Пока не добавлен ни один сайт</p>
        @else
            <table class="table">
            <thead class="table-light">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Имя</th>
                <th scope="col">Последняя проверка</th>
                <th scope="col">Код ответа</th>
            </tr>
            </thead>
                <tbody>
                @for(var url : page.getUrls())
                    <tr>
                        <th scope="row">${url.getId()}</th>
                        <td><a href="${NamedRoutes.urlsPath(url.getId())}">${url.getName()}</a></td>
                        !{var urlData = page.getChecks().get(url.getId());}
                        @if(urlData != null)
                            <td>${urlData.getCreatedAt().toLocalDateTime().format(formatter)}</td>
                            <td>${urlData.getStatusCode()}</td>
                        @else
                            <td> </td>
                            <td> </td>
                            </tr>
                        @endif
                @endfor
                </tbody>
            </table>
        @endif
    </div>
    `
)
