@import hexlet.code.utils.NamedRoutes
@import  hexlet.code.dto.BasePage
@param BasePage page


@template.layout.page(
content = @`
    @if(page.getFlash() != null)
        <div class="alert alert-${page.getFlashType()}" role="alert">
            ${page.getFlash()}
        </div>
    @endif
    <section class="text-bg-light p-3" style="height: 25rem; width: 100%">
        <div class="container" style="margin-top: 10rem;"> <!-- position-absolute top-50 start-50 translate-middle -->
            <div>
                <h1 class="fw-normal">Анализатор страниц</h1>
                <p class="fw-normal">Бесплатно проверяйте сайты на SEO пригодность</p>
            </div>

            <form class="row g-1" action="${NamedRoutes.urlsPath()}" method="post" style="margin-top: 15px">
                <div class="input-group">
                    <input type="url" class="form-control" placeholder="Ссылка" name="name">
                    <button type="submit" class="btn btn-secondary" type="button">Проверить</button>
                </div>
            </form>

            <div>
                <p class="fw-normal lh-lg text-body-secondary">Пример: https://www.example.com</p>
            </div>
        </div>
    </section>
    `
)
