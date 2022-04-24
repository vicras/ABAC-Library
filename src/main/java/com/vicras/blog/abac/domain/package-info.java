package com.vicras.blog.abac.domain;

/**
 * Writer
 * <p>
 * Create:
 * Can create new document from 8:00 to 13:00
 * Edit:
 * only document user
 * any time
 * Delete:
 * only document user
 * any time
 * View:
 * only document user
 * only own elements
 * <p>
 * Reader
 * <p>
 * View:
 * only approved documents
 * <p>
 * Redactor
 * <p>
 * - Add new users
 * - Approve document 13:00 - 18:00
 * - View
 * * users
 * * all documents
 * * documents for approve
 */