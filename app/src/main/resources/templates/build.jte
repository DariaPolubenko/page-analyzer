@import hexlet.code.utils.NamedRoutes
@import  hexlet.code.dto.BuildUrlsPage
@param BuildUrlsPage page


@template.layout.page(
content = @`
    @if(page.getFlash() != null)
        <div class="alert alert-${page.getFlashType()}" role="alert">
            ${page.getFlash()}
        </div>
    @endif
    <div class="container">
        <div>
            <h1>Анализатор страниц</h1>
            <p>Бесплатно проверяйте сайты на SEO пригодность</p>
        </div>

        <form class="row g-1" action="${NamedRoutes.urlsPath()}" method="post" style="margin-top: 15px">
            <div class="input-group">
                <input type="url" class="form-control" placeholder="Ссылка" name="name" value="${page.getName()}">
                <button type="submit" class="btn btn-outline-secondary" type="button">Проверить</button>
            </div>
        </form>

        <div>
            <p>Пример: https://www.example.com</p>
        </div>
    </div>
    `
)


<!-- <div class="mb-3">
    <label>
        <input type="url" class="form-control" placeholder="Ссылка" name="name" value="${page.getName()}"/>
    </label>
</div>
<input type="submit" class="btn btn-primary mb-3" value="Проверить"/>


 <div class="col-auto">
                <label for="inputLink" class="visually-hidden">Ссылка</label>
                <input type="url" class="form-control" id="inputLink" placeholder="Ссылка" name="name" value="${page.getName()}"/>
            </div>

            <div class="col-auto">
                <button type="submit" class="btn btn-primary mb-3">Проверить</button>
            </div>

-->
