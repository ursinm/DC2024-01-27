from django.urls import path, include
from rest_framework import routers

from .views import NoteViewSet

urlpatterns = [
    path('notes', NoteViewSet.as_view(), name="notes"),
    path('notes/<int:pk>', NoteViewSet.as_view(), name="notes_get"),
]