window.addEventListener('DOMContentLoaded', () => {
    const tabla = document.getElementById('datatablesSimple');
    const tbody = tabla.querySelector('tbody');

    // Función para listar médicos
    function listarMedicos() {
        fetch('medicoController?action=listar')
            .then(response => response.json())
            .then(data => {
                tbody.innerHTML = '';
                data.forEach(medico => {
                    const fecha = new Date(medico.fechNaciMedi).toISOString().split('T')[0];
                    const row = `
                        <tr>
                            <td>${medico.codiMedi}</td>
                            <td>${medico.ndniMedi}</td>
                            <td>${medico.appaMedi}</td>
                            <td>${medico.apmaMedi}</td>
                            <td>${medico.nombMedi}</td>
                            <td>${fecha}</td>
                            <td>${medico.logiMedi}</td>
                            <td>
                                <button class="btn btn-primary btnEditar" data-id="${medico.codiMedi}">Editar</button>
                            </td>
                        </tr>
                    `;
                    tbody.insertAdjacentHTML('beforeend', row);
                });

                // Inicializar datatable si no existe aún
                if (!tabla.classList.contains('datatable-loaded')) {
                    new simpleDatatables.DataTable(tabla);
                    tabla.classList.add('datatable-loaded');
                }
            })
            .catch(err => console.error('Error al listar médicos:', err));
    }

    // Llamar al listado inicial
    listarMedicos();

    // Mostrar modal Agregar
    document.getElementById('btnAgregar').addEventListener('click', () => {
        const form = document.getElementById('formAgregarMedico');
        form.reset();
        new bootstrap.Modal(document.getElementById('modalAgregar')).show();
    });

    // Enviar formulario Agregar
    document.getElementById('formAgregarMedico').addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        formData.append('action', 'agregar');

        fetch('medicoController', {
            method: 'POST',
            body: new URLSearchParams(formData)
        }).then(res => res.text()).then(() => {
            listarMedicos(); // Refresca la tabla
            bootstrap.Modal.getInstance(document.getElementById('modalAgregar')).hide();
        });
    });

    // Mostrar modal Editar
    tbody.addEventListener('click', (e) => {
        if (e.target.classList.contains('btnEditar')) {
            const id = e.target.getAttribute('data-id');
            fetch(`medicoController?action=buscar&id=${id}`)
                .then(res => res.json())
                .then(data => {
                    const form = document.getElementById('formEditarMedico');
                    form.codiMedi.value = data.codiMedi;
                    form.ndniMedi.value = data.ndniMedi;
                    form.appaMedi.value = data.appaMedi;
                    form.apmaMedi.value = data.apmaMedi;
                    form.nombMedi.value = data.nombMedi;
                    form.fechNaciMedi.value = data.fechNaciMedi.split('T')[0]; // formato yyyy-MM-dd
                    form.logiMedi.value = data.logiMedi;

                    new bootstrap.Modal(document.getElementById('modalEditar')).show();
                });
        }
    });

    // Enviar formulario Editar
    document.getElementById('formEditarMedico').addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        formData.append('action', 'editar');

        fetch('medicoController', {
            method: 'POST',
            body: new URLSearchParams(formData)
        }).then(res => res.text()).then(() => {
            listarMedicos(); // Refresca la tabla
            bootstrap.Modal.getInstance(document.getElementById('modalEditar')).hide();
        });
    });
});
