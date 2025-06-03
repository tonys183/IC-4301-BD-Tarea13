document.addEventListener('DOMContentLoaded', () => {
    const listaLibrosDiv = document.getElementById('lista-libros');
    const prestamoForm = document.getElementById('prestamo-form');
    const prestamoMensajeDiv = document.getElementById('prestamo-mensaje');
    
    const libroIdDisplayInput = document.getElementById('libro-id-display');
    const libroIdHiddenInput = document.getElementById('libro-id-prestamo');
    const usuarioSelect = document.getElementById('usuario-id-prestamo');

    function mostrarMensaje(mensaje, tipo) {
        prestamoMensajeDiv.textContent = mensaje;
        prestamoMensajeDiv.className = '';
        prestamoMensajeDiv.classList.add(`mensaje-${tipo}`);
        setTimeout(() => {
            prestamoMensajeDiv.textContent = '';
            prestamoMensajeDiv.className = '';
        }, 5000);
    }

    async function cargarUsuarios() {
        try {
            const response = await fetch('/api/usuarios');
            if (!response.ok) { throw new Error(`Error al cargar usuarios: ${response.status} ${response.statusText}`); }
            const usuarios = await response.json();
            
            usuarioSelect.innerHTML = '<option value="" disabled selected>Seleccione un usuario</option>';
            if (usuarios.length === 0) {
                usuarioSelect.innerHTML += '<option value="" disabled>No hay usuarios registrados</option>';
                return;
            }

            usuarios.forEach(usuario => {
                const option = document.createElement('option');
                option.value = usuario.userId;
                option.textContent = usuario.name;
                usuarioSelect.appendChild(option);
            });

        } catch (error) {
            usuarioSelect.innerHTML = '<option value="" disabled selected>Error al cargar usuarios</option>';
            mostrarMensaje('Error al cargar los usuarios: ' + error.message, 'error');
        }
    }

    async function cargarLibros() {
        listaLibrosDiv.innerHTML = '';
        try {
            const response = await fetch('/api/libros');
            if (!response.ok) { throw new Error(`Error al cargar libros: ${response.status} ${response.statusText}`);}
            const libros = await response.json();

            if (libros.length === 0) {
                listaLibrosDiv.innerHTML = '<p>No hay libros disponibles</p>';
                return;
            }

            libros.forEach(libro => {
                const libroCard = document.createElement('div');
                libroCard.classList.add('libro-card');
                libroCard.innerHTML = `
                    <h3>${libro.title}</h3>
                    <p><strong>Autor:</strong> ${libro.author}</p>
                    <p><strong>ISBN:</strong> ${libro.isbn}</p>
                    <p><strong>Disponibles:</strong> ${libro.availableCopies} / ${libro.totalCopies}</p>
                    <p><strong>Estado:</strong> <span class="status-${libro.status ? libro.status.toLowerCase() : 'desconocido'}">${libro.status || 'Desconocido'}</span></p>
                    <button class="prestar-btn" data-id="${libro.bookId}" data-title="${libro.title}" ${libro.availableCopies === 0 || libro.status !== 'AVAILABLE' ? 'disabled' : ''}>
                        Solicitar Préstamo
                    </button>
                `;
                listaLibrosDiv.appendChild(libroCard);
            });

            document.querySelectorAll('.prestar-btn').forEach(button => {
                button.addEventListener('click', (event) => {
                    const libroId = event.target.dataset.id;
                    const libroTitle = event.target.dataset.title;
                    libroIdHiddenInput.value = libroId;
                    libroIdDisplayInput.value = libroTitle;
                    
                    document.getElementById('realizar-prestamo').scrollIntoView({ behavior: 'smooth' });
                });
            });

        } catch (error) {
            listaLibrosDiv.innerHTML = '<p>Error al cargar los libros</p>';
            mostrarMensaje('No se pudieron cargar los libros: ' + error.message, 'error');
        }
    }

    prestamoForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const bookId = libroIdHiddenInput.value;
        const userId = usuarioSelect.value;

        if (!bookId) {
            mostrarMensaje('Seleccione un libro', 'error');
            return;
        }
        if (!userId) {
            mostrarMensaje('Seleccione un usuario', 'error');
            return;
        }

        try {
            const response = await fetch('/api/prestamos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ 
                    bookId: parseInt(bookId, 10),
                    userId: parseInt(userId, 10)
                }),
            });

            const responseData = await response.json();

            if (!response.ok) { throw new Error(responseData.error || `Error al procesar el préstamo: ${response.status}`); }

            const selectedUserText = usuarioSelect.options[usuarioSelect.selectedIndex].text;
            mostrarMensaje(`Préstamo del libro "${libroIdDisplayInput.value}" (ID: ${bookId}), realizado con éxito para el usuario "${selectedUserText}" - ID Préstamo: ${responseData.loanId}`, 'exito');
            prestamoForm.reset();
            libroIdDisplayInput.value = '';
            usuarioSelect.value = "";
            await cargarLibros(); 

        } catch (error) {
            mostrarMensaje(error.message, 'error');
        }
    });

    cargarLibros();
    cargarUsuarios();
}); 