@import hexlet.code.utils.NamedRoutes
@import  hexlet.code.dto.BuildUrlsPage
@param BuildUrlsPage page


@template.layout.page(
content = @`
<div>
    <p>Тут будет главная страница</p>
        @if(page.getError() != null)
            <ul>
                <li>${page.getError()}</li>
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
`
)
