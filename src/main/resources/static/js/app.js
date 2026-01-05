
const API = {
  books: '/api/books',
  members: '/api/members',
  loans: '/api/loans'
};

function getCsrf() {
  const tokenMeta = document.querySelector('meta[name="_csrf"]');
  const headerMeta = document.querySelector('meta[name="_csrf_header"]');
  if (tokenMeta && headerMeta) {
    return { header: headerMeta.content, token: tokenMeta.content };
  }
  return null;
}

async function handleResponse(res) {
  if (!res.ok) {
    const text = await res.text().catch(() => null);
    let msg = text || `HTTP ${res.status}`;
    try {
      const json = text ? JSON.parse(text) : null;
      msg = json?.message || json?.error || msg;
    } catch (e) {
    }
    const err = new Error(msg || (`HTTP ${res.status}`));
    err.status = res.status;
    err.raw = text;
    throw err;
  }
  if (res.status === 204) return null;
  try {
    return await res.json();
  } catch (e) {
    return await res.text().catch(() => null);
  }
}

function headersFor(method = 'GET') {
  const headers = { 'Accept': 'application/json' };
  const m = (method || 'GET').toUpperCase();
  const hasBody = ['POST', 'PUT', 'PATCH', 'DELETE'].includes(m) && m !== 'GET';
  if (hasBody) headers['Content-Type'] = 'application/json';
  const csrf = getCsrf();
  if (csrf) headers[csrf.header] = csrf.token;
  return headers;
}

function normalizeListResponse(data) {
  if (!data) return [];
  if (Array.isArray(data)) return data;
  if (Array.isArray(data.content)) return data.content;
  if (Array.isArray(data.items)) return data.items;
  if (Array.isArray(data.data)) return data.data;
  return [];
}

function withPageable(url, pageable) {
  if (!pageable) return url;
  return url.includes('?') ? `${url}&${pageable}` : `${url}?${pageable}`;
}

async function getBooks(options = {}) {
  const url = withPageable(API.books, options.pageable);
  const res = await fetch(url, { method: 'GET', headers: headersFor('GET') });
  const data = await handleResponse(res);
  if (options.raw) return data;
  return normalizeListResponse(data);
}
async function getBooksRaw(pageable) {
  const url = withPageable(API.books, pageable);
  const res = await fetch(url, { method: 'GET', headers: headersFor('GET') });
  return handleResponse(res);
}
async function getBook(id) {
  const res = await fetch(`${API.books}/${id}`, { method: 'GET', headers: headersFor('GET') });
  return handleResponse(res);
}
async function createBook(payload) {
  const res = await fetch(API.books, { method: 'POST', headers: headersFor('POST'), body: JSON.stringify(payload) });
  return handleResponse(res);
}
async function updateBook(id, payload) {
  const res = await fetch(`${API.books}/${id}`, { method: 'PUT', headers: headersFor('PUT'), body: JSON.stringify(payload) });
  return handleResponse(res);
}
async function deleteBook(id) {
  const res = await fetch(`${API.books}/${id}`, { method: 'DELETE', headers: headersFor('DELETE') });
  return handleResponse(res);
}

async function getMembers(options = {}) {
  const url = withPageable(API.members, options.pageable);
  const res = await fetch(url, { method: 'GET', headers: headersFor('GET') });
  const data = await handleResponse(res);
  if (options.raw) return data;
  return normalizeListResponse(data);
}
async function getMembersRaw(pageable) {
  const url = withPageable(API.members, pageable);
  const res = await fetch(url, { method: 'GET', headers: headersFor('GET') });
  return handleResponse(res);
}
async function getMember(id) {
  const res = await fetch(`${API.members}/${id}`, { method: 'GET', headers: headersFor('GET') });
  return handleResponse(res);
}
async function createMember(payload) {
  const res = await fetch(API.members, { method: 'POST', headers: headersFor('POST'), body: JSON.stringify(payload) });
  return handleResponse(res);
}
async function updateMember(id, payload) {
  const res = await fetch(`${API.members}/${id}`, { method: 'PUT', headers: headersFor('PUT'), body: JSON.stringify(payload) });
  return handleResponse(res);
}
async function deleteMember(id) {
  const res = await fetch(`${API.members}/${id}`, { method: 'DELETE', headers: headersFor('DELETE') });
  return handleResponse(res);
}

async function getLoans(options = {}) {
  const url = withPageable(API.loans, options.pageable);
  const res = await fetch(url, { method: 'GET', headers: headersFor('GET') });
  const data = await handleResponse(res);
  if (options.raw) return data;
  return normalizeListResponse(data);
}
async function getLoansRaw(pageable) {
  const url = withPageable(API.loans, pageable);
  const res = await fetch(url, { method: 'GET', headers: headersFor('GET') });
  return handleResponse(res);
}
async function getLoan(id) {
  const res = await fetch(`${API.loans}/${id}`, { method: 'GET', headers: headersFor('GET') });
  return handleResponse(res);
}
async function createLoan(payload) {
  const res = await fetch(API.loans, { method: 'POST', headers: headersFor('POST'), body: JSON.stringify(payload) });
  return handleResponse(res);
}
async function returnLoan(id) {
  const res = await fetch(`${API.loans}/${id}/return`, { method: 'POST', headers: headersFor('POST') });
  return handleResponse(res);
}
async function updateLoan(id, payload) {
  const res = await fetch(`${API.loans}/${id}`, { method: 'PUT', headers: headersFor('PUT'), body: JSON.stringify(payload) });
  return handleResponse(res);
}
async function deleteLoan(id) {
  const res = await fetch(`${API.loans}/${id}`, { method: 'DELETE', headers: headersFor('DELETE') });
  return handleResponse(res);
}

function showMessage(containerId, message, type = 'info') {
  const c = document.getElementById(containerId);
  if (!c) return;
  const color = type === 'danger' ? '#ffe5e5' : (type === 'success' ? '#e6ffef' : '#eef2ff');
  c.innerHTML = `<div style="padding:10px;border-radius:8px;background:${color};margin-bottom:10px;">${escapeHtml(message)}</div>`;
}
function escapeHtml(s) {
  return ('' + (s || '')).replace(/[&<>"'`=\/]/g, function (c) {
    return { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;', '/': '&#x2F;', '`': '&#x60;', '=': '&#x3D;' }[c];
  });
}

function showErrors(containerId, err) {
  const c = document.getElementById(containerId);
  if (!c) return;
  let html = '';
  if (!err) {
    html = '<div>Erreur inconnue</div>';
  } else {
    if (err.raw) {
      try {
        const parsed = JSON.parse(err.raw);
        if (Array.isArray(parsed.errors)) {
          html = parsed.errors.map(e => `<div>${escapeHtml(e.defaultMessage || e.message || JSON.stringify(e))}</div>`).join('');
        } else if (parsed.fieldErrors) {
          html = Object.values(parsed.fieldErrors).flat().map(m => `<div>${escapeHtml(m)}</div>`).join('');
        } else if (parsed.message) {
          html = `<div>${escapeHtml(parsed.message)}</div>`;
        } else {
          html = `<div>${escapeHtml(JSON.stringify(parsed))}</div>`;
        }
      } catch (e) {
        html = `<div>${escapeHtml(err.raw || err.message || String(err))}</div>`;
      }
    } else {
      html = `<div>${escapeHtml(err.message || String(err))}</div>`;
    }
  }
  c.innerHTML = `<div style="padding:10px;border-radius:8px;background:#ffecec">${html}</div>`;
}

async function submitBookForm(ev) {
  if (ev && ev.preventDefault) ev.preventDefault();
  const id = document.getElementById('bookId')?.value;
  const payload = {
    title: (document.getElementById('inputTitle')?.value || '').trim(),
    author: (document.getElementById('inputAuthor')?.value || '').trim(),
    isbn: (document.getElementById('inputIsbn')?.value || '').trim(),
    description: (document.getElementById('inputDesc')?.value || '').trim()
  };
  const messageId = 'message';
  try {
    if (!payload.title || !payload.author) {
      showMessage(messageId, 'Titre et auteur requis', 'danger');
      return;
    }
    let res;
    if (id) {
      res = await updateBook(id, payload);
      showMessage(messageId, 'Livre mis à jour', 'success');
    } else {
      res = await createBook(payload);
      showMessage(messageId, 'Livre créé', 'success');
      setTimeout(() => location.href = '/books/list.html', 800);
    }
    return res;
  } catch (err) {
    console.error('submitBookForm error', err);
    showErrors(messageId, err);
    throw err;
  }
}

async function deleteBookHandler(id, rowElement) {
  if (!confirm('Supprimer ce livre ?')) return;
  try {
    await deleteBook(id);
    showMessage('message', 'Livre supprimé', 'success');
    if (rowElement && rowElement.remove) rowElement.remove();
  } catch (err) {
    console.error('deleteBookHandler error', err);
    showErrors('message', err);
  }
}

async function submitMemberForm(ev) {
  if (ev && ev.preventDefault) ev.preventDefault();
  const id = document.getElementById('memberId')?.value;

  const payload = {
    prenom: (document.getElementById('prenom')?.value || '').trim(),
    nom: (document.getElementById('nom')?.value || '').trim(),
    email: (document.getElementById('email')?.value || '').trim(),
    telephone: (document.getElementById('phone')?.value || '').trim()
  };

  const messageId = 'message';

  try {
    if (!payload.prenom || !payload.nom) {
      showMessage(messageId, 'Prénom et nom requis', 'danger');
      return;
    }

    let res;
    if (id) {
      res = await updateMember(id, payload);
      showMessage(messageId, 'Membre mis à jour', 'success');
    } else {
      res = await createMember(payload);
      showMessage(messageId, 'Membre créé', 'success');
      setTimeout(() => location.href = '/members/list.html', 800);
    }
    return res;
  } catch (err) {
    console.error('submitMemberForm error', err);
    showErrors(messageId, err);
    throw err;
  }
}


async function deleteMemberHandler(id, rowElement) {
  if (!confirm('Supprimer ce membre ?')) return;
  try {
    await deleteMember(id);
    showMessage('message', 'Membre supprimé', 'success');
    if (rowElement && rowElement.remove) rowElement.remove();
  } catch (err) {
    console.error('deleteMemberHandler error', err);
    showErrors('message', err);
  }
}
async function submitLoanForm(ev) {
  if (ev && ev.preventDefault) ev.preventDefault();

  const id = document.getElementById('loanId')?.value;
  const bookId = document.getElementById('selectBook')?.value;
  const memberId = document.getElementById('selectMember')?.value;
  const loanDate = document.getElementById('borrowDate')?.value;
  const dueDate = document.getElementById('dueDate')?.value;
  const messageId = 'message';

  if (!bookId || !memberId || !loanDate || !dueDate) {
    showMessage(messageId, 'Tous les champs sont requis', 'danger');
    return;
  }


  const payload = {
    bookId: Number(document.getElementById('selectBook').value),
    memberId: Number(document.getElementById('selectMember').value),
    loanDate: document.getElementById('borrowDate').value,
    dueDate: document.getElementById('dueDate').value
  };



  try {
    let res;

    if (!id) {

      try {
        res = await createLoan(payload);
      } catch (err) {
        if (err && err.status === 400) {
          console.warn('createLoan A failed, trying B', err);
          res = await createLoan(payload);
        } else {
          throw err;
        }
      }

      showMessage(messageId, 'Emprunt créé', 'success');
      setTimeout(() => location.href = '/loans/list.html', 900);

    } else {

      try {
        res = await updateLoan(id, payload);
      } catch (err) {
        if (err && err.status === 400) {
          res = await updateLoan(id, payload);
        } else {
          throw err;
        }
      }

      showMessage(messageId, 'Emprunt mis à jour', 'success');
    }

    return res;

  } catch (err) {
    console.error('submitLoanForm error', err);
    showErrors(messageId, err);
    throw err;
  }
}


async function returnLoanHandler(id, rowElement) {
  if (!confirm('Marquer ce prêt comme retourné ?')) return;
  const messageId = 'message';
  try {
    await returnLoan(id);
    showMessage(messageId, 'Prêt marqué rendu', 'success');
    if (rowElement) {
      const statusCell = rowElement.querySelector('td.status');
      if (statusCell) statusCell.innerHTML = '<span class="badge badge-success">Rendu</span>';
    }
  } catch (err) {
    console.error('returnLoanHandler error', err);
    showErrors(messageId, err);
  }
}

async function deleteLoanHandler(id, rowElement) {
  if (!confirm('Supprimer ce prêt ?')) return;
  const messageId = 'message';
  try {
    await deleteLoan(id);
    showMessage(messageId, 'Prêt supprimé', 'success');
    if (rowElement && rowElement.remove) rowElement.remove();
  } catch (err) {
    console.error('deleteLoanHandler error', err);
    showErrors(messageId, err);
  }
}

document.addEventListener('DOMContentLoaded', () => {
  const bookForm = document.getElementById('bookForm');
  if (bookForm) bookForm.addEventListener('submit', submitBookForm);

  const memberForm = document.getElementById('memberForm');
  if (memberForm) memberForm.addEventListener('submit', submitMemberForm);

  const loanForm = document.getElementById('loanForm');
  if (loanForm) loanForm.addEventListener('submit', submitLoanForm);
});

window.apiHelpers = {
  getBooks, getBooksRaw, getBook, createBook, updateBook, deleteBook,
  getMembers, getMembersRaw, getMember, createMember, updateMember, deleteMember,
  getLoans, getLoansRaw, getLoan, createLoan, returnLoan, updateLoan, deleteLoan,
  showMessage, escapeHtml,
  showErrors,
  submitBookForm, deleteBookHandler,
  submitMemberForm, deleteMemberHandler,
  submitLoanForm, returnLoanHandler, deleteLoanHandler
};

window.formsHandlers = {
  submitBookForm, deleteBookHandler,
  submitMemberForm, deleteMemberHandler,
  submitLoanForm, returnLoanHandler, deleteLoanHandler
};